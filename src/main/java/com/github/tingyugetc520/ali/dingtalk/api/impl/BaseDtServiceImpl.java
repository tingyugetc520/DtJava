package com.github.tingyugetc520.ali.dingtalk.api.impl;

import com.github.tingyugetc520.ali.dingtalk.api.*;
import com.github.tingyugetc520.ali.dingtalk.constant.DtConstant;
import com.github.tingyugetc520.ali.dingtalk.config.DtConfigStorage;
import com.github.tingyugetc520.ali.dingtalk.error.DtError;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;
import com.github.tingyugetc520.ali.dingtalk.error.DtRuntimeException;
import com.github.tingyugetc520.ali.dingtalk.util.DataUtils;
import com.github.tingyugetc520.ali.dingtalk.util.crypto.DtCryptUtil;
import com.github.tingyugetc520.ali.dingtalk.util.http.RequestExecutor;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.github.tingyugetc520.ali.dingtalk.error.DtErrorMsgEnum.*;

@Slf4j
public abstract class BaseDtServiceImpl implements DtService {
    private final DtUserService userService = new DtUserServiceImpl(this);
    private final DtDepartmentService departmentService = new DtDepartmentServiceImpl(this);
    private final DtOAuth2Service oauth2Service = new DtOAuth2ServiceImpl(this);
    private final DtAgentService agentService = new DtAgentServiceImpl(this);
    private final DtCorpConversationMessageService corpConversationMsgService = new DtCorpConversationMessageServiceImpl(this);

    /**
     * 全局的是否正在刷新access token的锁.
     */
    protected final Object globalAccessTokenRefreshLock = new Object();

    protected DtConfigStorage configStorage;

    private final int retrySleepMillis = 1000;
    private final int maxRetryTimes = 5;

    @Override
    public boolean checkSignature(String signature, String timestamp, String nonce, String data) {
        try {
            return DtCryptUtil.getSignature(
                    this.configStorage.getToken(), timestamp, nonce, data
            ).equals(signature);
        } catch (Exception e) {
            log.error("Checking signature failed, and the reason is :" + e.getMessage());
            return false;
        }
    }

    @Override
    public String getAccessToken() throws DtErrorException {
        return getAccessToken(false);
    }

    @Override
    public String get(String url, String queryParam) throws DtErrorException {
        RequestExecutor<String, String> executor = getOkHttpSimpleGetRequestExecutor();
        return execute(executor, url, queryParam);
    }

    @Override
    public String post(String url, String postData) throws DtErrorException {
        RequestExecutor<String, String> executor = getOkHttpSimplePostRequestExecutor();
        return execute(executor, url, postData);
    }

    @Override
    public String post(String url, JsonObject jsonObject) throws DtErrorException {
        return this.post(url, jsonObject.toString());
    }

    @Override
    public String post(String url, Object obj) throws DtErrorException {
        return this.post(url, obj.toString());
    }

    /**
     * okHttp get请求
     * @return executor
     */
    protected abstract RequestExecutor<String, String> getOkHttpSimpleGetRequestExecutor();

    /**
     * okHttp post请求
     * @return executor
     */
    protected abstract RequestExecutor<String, String> getOkHttpSimplePostRequestExecutor();

    /**
     * 发送请求，在这里执行的策略是当发生access_token过期时才去刷新，然后重新执行请求，而不是全局定时请求.
     */
    @Override
    public <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data) throws DtErrorException {
        int retryTimes = 0;
        do {
            try {
                return executeInternal(executor, uri, data);
            } catch (DtErrorException e) {
                if (retryTimes + 1 > maxRetryTimes) {
                    log.warn("重试达到最大次数【{}】", maxRetryTimes);
                    //最后一次重试失败后，直接抛出异常，不再等待
                    throw new DtRuntimeException("钉钉服务端异常，超出重试次数");
                }

                DtError error = e.getError();
                // 系统繁忙, 1000ms后重试
                if (error.getErrorCode() == CODE_1.getCode()) {
                    int sleepMillis = retrySleepMillis * (1 << retryTimes);
                    try {
                        log.debug("钉钉系统繁忙，{} ms 后重试(第{}次)", sleepMillis, retryTimes + 1);
                        Thread.sleep(sleepMillis);
                    } catch (InterruptedException e1) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    throw e;
                }
            }
        } while (retryTimes++ < maxRetryTimes);

        log.warn("重试达到最大次数【{}】", maxRetryTimes);
        throw new DtRuntimeException("钉钉服务端异常，超出重试次数");
    }

    protected <T, E> T executeInternal(RequestExecutor<T, E> executor, String uri, E data) throws DtErrorException {
        E dataForLog = DataUtils.handleDataWithSecret(data);

        if (uri.contains("access_token=")) {
            throw new IllegalArgumentException("uri参数中不允许有access_token: " + uri);
        }
        String accessToken = getAccessToken(false);
        String uriWithAccessToken = uri + (uri.contains("?") ? "&" : "?") + "access_token=" + accessToken;

        try {
            T result = executor.execute(uriWithAccessToken, data);
            log.debug("\n【请求地址】: {}\n【请求参数】：{}\n【响应数据】：{}", uriWithAccessToken, dataForLog, result);
            return result;
        } catch (DtErrorException e) {
            DtError error = e.getError();

            if (DtConstant.ACCESS_TOKEN_ERROR_CODES.contains(error.getErrorCode())) {
                // 强制设置DtConfigStorage它的access token过期了，这样在下一次请求里就会刷新access token
                this.configStorage.expireAccessToken();
                if (this.getDtConfigStorage().autoRefreshToken()) {
                    log.warn("即将重新获取新的access_token，错误代码：{}，错误信息：{}", error.getErrorCode(), error.getErrorMsg());
                    return this.execute(executor, uri, data);
                }
            }

            if (error.getErrorCode() != 0) {
                log.error("\n【请求地址】: {}\n【请求参数】：{}\n【错误信息】：{}", uriWithAccessToken, dataForLog, error);
                throw new DtErrorException(error, e);
            }
            return null;
        } catch (IOException e) {
            log.error("\n【请求地址】: {}\n【请求参数】：{}\n【异常信息】：{}", uriWithAccessToken, dataForLog, e.getMessage());
            throw new DtRuntimeException(e);
        }
    }

    @Override
    public void setDtConfigStorage(DtConfigStorage dtConfigStorage) {
        this.configStorage = dtConfigStorage;
        this.initHttp();
    }

    @Override
    public DtDepartmentService getDepartmentService() {
        return departmentService;
    }

    @Override
    public DtOAuth2Service getOauth2Service() {
        return oauth2Service;
    }

    @Override
    public DtUserService getUserService() {
        return userService;
    }

    @Override
    public DtAgentService getAgentService() {
        return agentService;
    }

    @Override
    public DtCorpConversationMessageService getCorpConversationMsgService() {
        return corpConversationMsgService;
    }

}
