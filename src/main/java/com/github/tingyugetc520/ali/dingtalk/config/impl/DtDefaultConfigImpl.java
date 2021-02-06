package com.github.tingyugetc520.ali.dingtalk.config.impl;

import com.github.tingyugetc520.ali.dingtalk.bean.DtAccessToken;
import com.github.tingyugetc520.ali.dingtalk.config.DtConfigStorage;
import com.github.tingyugetc520.ali.dingtalk.util.http.HttpProxyType;
import com.github.tingyugetc520.ali.dingtalk.util.json.DtGsonBuilder;
import com.github.tingyugetc520.ali.dingtalk.constant.DtApiPathConstant;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于内存的配置provider，在实际生产环境中应该将这些配置持久化.
 */
public class DtDefaultConfigImpl implements DtConfigStorage, Serializable {
    private static final long serialVersionUID = 1154541446729462780L;

    private volatile String corpId;
    private volatile Long agentId;
    private volatile String appKey;
    private volatile String appSecret;

    protected volatile String accessToken;
    protected transient Lock accessTokenLock = new ReentrantLock();
    private volatile long expiresTime;

    /**
     * 反向代理与正向代理
     */
    private volatile int httpProxyType;
    private volatile String httpProxyServer;
    private volatile String httpProxyHost;
    private volatile int httpProxyPort;
    private volatile String httpProxyUsername;
    private volatile String httpProxyPassword;

    private volatile String baseApiUrl;

    @Override
    public void setBaseApiUrl(String baseUrl) {
        this.baseApiUrl = baseUrl;
    }

    @Override
    public String getApiUrl(String path) {
        if (baseApiUrl == null) {
            // 反向代理
            HttpProxyType proxyType = HttpProxyType.findByCode(getHttpProxyType());
            if (HttpProxyType.REVERSE.equals(proxyType)) {
                baseApiUrl = httpProxyServer;
            } else {
                baseApiUrl = DtApiPathConstant.DEFAULT_DT_BASE_URL;
            }
        }
        return baseApiUrl + path;
    }

    @Override
    public String getAccessToken() {
        return this.accessToken;
    }

    @Override
    public Lock getAccessTokenLock() {
        return this.accessTokenLock;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public boolean isAccessTokenExpired() {
        return System.currentTimeMillis() > this.expiresTime;
    }

    @Override
    public void expireAccessToken() {
        this.expiresTime = 0;
    }

    @Override
    public synchronized void updateAccessToken(DtAccessToken accessToken) {
        updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
    }

    @Override
    public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
        this.accessToken = accessToken;
        this.expiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
    }

    @Override
    public String getCorpId() {
        return this.corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    @Override
    public Long getAgentId() {
        return this.agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    @Override
    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    @Override
    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    @Override
    public long getExpiresTime() {
        return this.expiresTime;
    }

    public void setExpiresTime(long expiresTime) {
        this.expiresTime = expiresTime;
    }

    @Override
    public int getHttpProxyType() {
        return this.httpProxyType;
    }

    public void setHttpProxyType(int httpProxyType) {
        this.httpProxyType = httpProxyType;
    }

    @Override
    public String getHttpProxyServer() {
        return this.httpProxyServer;
    }

    public void setHttpProxyServer(String httpProxyServer) {
        this.httpProxyServer = httpProxyServer;
    }

    @Override
    public String getHttpProxyHost() {
        return this.httpProxyHost;
    }

    public void setHttpProxyHost(String httpProxyHost) {
        this.httpProxyHost = httpProxyHost;
    }

    @Override
    public int getHttpProxyPort() {
        return this.httpProxyPort;
    }

    public void setHttpProxyPort(int httpProxyPort) {
        this.httpProxyPort = httpProxyPort;
    }

    @Override
    public String getHttpProxyUsername() {
        return this.httpProxyUsername;
    }

    public void setHttpProxyUsername(String httpProxyUsername) {
        this.httpProxyUsername = httpProxyUsername;
    }

    @Override
    public String getHttpProxyPassword() {
        return this.httpProxyPassword;
    }

    public void setHttpProxyPassword(String httpProxyPassword) {
        this.httpProxyPassword = httpProxyPassword;
    }

    @Override
    public boolean autoRefreshToken() {
        return true;
    }

    @Override
    public String toString() {
        return DtGsonBuilder.create().toJson(this);
    }

}
