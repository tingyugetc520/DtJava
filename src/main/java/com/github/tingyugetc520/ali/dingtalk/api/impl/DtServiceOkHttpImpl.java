package com.github.tingyugetc520.ali.dingtalk.api.impl;

import com.github.tingyugetc520.ali.dingtalk.bean.DtAccessToken;
import com.github.tingyugetc520.ali.dingtalk.config.DtConfigStorage;
import com.github.tingyugetc520.ali.dingtalk.constant.DtApiPathConstant;
import com.github.tingyugetc520.ali.dingtalk.error.DtError;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;
import com.github.tingyugetc520.ali.dingtalk.error.DtRuntimeException;
import com.github.tingyugetc520.ali.dingtalk.util.http.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.Objects;

/**
 * @author .
 */
@Slf4j
public class DtServiceOkHttpImpl extends BaseDtServiceImpl {
    private OkHttpClient httpClient;
//    private OkHttpProxyInfo httpProxy;

    public OkHttpClient getRequestHttpClient() {
        return httpClient;
    }

//    public OkHttpProxyInfo getRequestHttpProxy() {
//        return httpProxy;
//    }

    @Override
    public String getAccessToken(boolean forceRefresh) throws DtErrorException {
        if (!configStorage.isAccessTokenExpired() && !forceRefresh) {
            return configStorage.getAccessToken();
        }

        synchronized (this.globalAccessTokenRefreshLock) {
            //得到httpClient
            OkHttpClient client = getRequestHttpClient();
            //请求的request
            Request request = new Request.Builder()
                    .url(String.format(configStorage.getApiUrl(DtApiPathConstant.GET_TOKEN), configStorage.getAppKey(), configStorage.getAppSecret()))
                    .get()
                    .build();
            String resultContent = null;
            try {
                Response response = client.newCall(request).execute();
                resultContent = Objects.requireNonNull(response.body()).string();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                throw new DtRuntimeException(e);
            }

            DtError error = DtError.fromJson(resultContent);
            if (error.getErrorCode() != 0) {
                throw new DtErrorException(error);
            }
            DtAccessToken accessToken = DtAccessToken.fromJson(resultContent);
            configStorage.updateAccessToken(accessToken.getAccessToken(),
                    accessToken.getExpiresIn());
        }
        return configStorage.getAccessToken();
    }

    @Override
    public void initHttp() {
        log.debug("DtServiceOkHttpImpl initHttp");

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        // 设置代理
        HttpProxyType httpProxyType = HttpProxyType.findByCode(configStorage.getHttpProxyType());
        if (HttpProxyType.FORWARD.equals(httpProxyType)) {
            // 正向代理
            initForwardProxy(clientBuilder);
        }
        httpClient = clientBuilder.addInterceptor(new ResponseHttpStatusInterceptor()).build();
    }

    private void initForwardProxy(OkHttpClient.Builder clientBuilder) {
        if (StringUtils.isBlank(configStorage.getHttpProxyHost()) || configStorage.getHttpProxyPort() <= 0) {
            return;
        }
        OkHttpProxyInfo httpProxy = OkHttpProxyInfo.httpProxy(
                configStorage.getHttpProxyHost(),
                configStorage.getHttpProxyPort(),
                configStorage.getHttpProxyUsername(),
                configStorage.getHttpProxyPassword()
        );
        clientBuilder.proxy(httpProxy.getProxy());

        //设置授权
        clientBuilder.authenticator((route, response) -> {
            String credential = Credentials.basic(httpProxy.getProxyUsername(), httpProxy.getProxyPassword());
            return response.request().newBuilder()
                    .header("Authorization", credential)
                    .build();
        });
    }

    @Override
    public DtConfigStorage getDtConfigStorage() {
        return this.configStorage;
    }

    @Override
    protected RequestExecutor<String, String> getOkHttpSimpleGetRequestExecutor() {
        return OkHttpSimpleGetRequestExecutor.create(httpClient);
    }

    @Override
    protected RequestExecutor<String, String> getOkHttpSimplePostRequestExecutor() {
        return OkHttpSimplePostRequestExecutor.create(httpClient);
    }


}
