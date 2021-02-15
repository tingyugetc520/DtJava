package com.github.tingyugetc520.ali.dingtalk.config;

import com.github.tingyugetc520.ali.dingtalk.bean.DtAccessToken;

import java.util.concurrent.locks.Lock;

/**
 * 钉钉应用配置
 */
public interface DtConfigStorage {

    /**
     * 设置钉钉服务器 baseUrl.
     * 默认值是 https://oapi.dingtalk.com, 如果使用默认值，则不需要调用 setBaseApiUrl
     *
     * @param baseUrl 钉钉服务器 Url
     */
    void setBaseApiUrl(String baseUrl);

    /**
     * 获取钉钉 API Url.
     */
    String getApiUrl(String path);

    String getAccessToken();

    Lock getAccessTokenLock();

    boolean isAccessTokenExpired();

    /**
     * 强制将access token过期掉.
     */
    void expireAccessToken();

    void updateAccessToken(DtAccessToken accessToken);

    void updateAccessToken(String accessToken, int expiresIn);

    String getCorpId();

    Long getAgentId();

    String getAppKey();

    String getAppSecret();

    String getAesKey();

    String getToken();

    String getAppKeyOrCorpId();

    long getExpiresTime();

    int getHttpProxyType();

    String getHttpProxyServer();

    String getHttpProxyHost();

    int getHttpProxyPort();

    String getHttpProxyUsername();

    String getHttpProxyPassword();

    /**
     * 是否自动刷新token
     *
     * @return .
     */
    boolean autoRefreshToken();

}
