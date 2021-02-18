package com.github.tingyugetc520.ali.dingtalk.api;

import com.github.tingyugetc520.ali.dingtalk.config.DtConfigStorage;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;
import com.github.tingyugetc520.ali.dingtalk.util.http.RequestExecutor;

public interface DtService extends BaseService {

    /**
     * <pre>
     * 验证推送过来的消息的正确性
     * </pre>
     *
     * @param signature 消息签名
     * @param timestamp    时间戳
     * @param nonce        随机数
     * @param data         传输过来的数据
     * @return the boolean
     */
    boolean checkSignature(String signature, String timestamp, String nonce, String data);

    /**
     * 获取access_token, 不强制刷新access_token
     *
     * @return the access token
     * @throws DtErrorException the error exception
     * @see #getAccessToken(boolean)
     */
    String getAccessToken() throws DtErrorException;

    /**
     * 获取access_token，本方法线程安全
     * 且在多线程同时刷新时只刷新一次，避免触发调用次数频繁限制
     * 非必要情况下尽量不要主动调用此方法
     *
     * @param forceRefresh 强制刷新
     * @return the access token
     * @throws DtErrorException the error exception
     */
    String getAccessToken(boolean forceRefresh) throws DtErrorException;

    /**
     * Service没有实现某个API的时候，可以用这个，
     * 比{@link #get}和{@link #post}方法更灵活，可以自己构造RequestExecutor用来处理不同的参数和不同的返回类型。
     *
     * @param <T>      请求值类型
     * @param <E>      返回值类型
     * @param executor 执行器
     * @param uri      请求地址
     * @param data     参数
     * @return the t
     * @throws DtErrorException the error exception
     */
    <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data) throws DtErrorException;

    /**
     * 初始化http请求对象
     */
    void initHttp();

    /**
     * 获取DtConfigStorage对象
     *
     * @return DtConfigStorage config storage
     */
    DtConfigStorage getDtConfigStorage();

    /**
     * 注入 {@link DtConfigStorage} 的实现
     *
     * @param configProvider 配置对象
     */
    void setDtConfigStorage(DtConfigStorage configProvider);

    /**
     * 获取部门相关接口的服务类对象
     * @return department service
     */
    DtDepartmentService getDepartmentService();

    /**
     * 获取用户相关接口的服务类对象
     *
     * @return the user service
     */
    DtUserService getUserService();

    /**
     * 获取Oauth2相关接口的服务类对象
     *
     * @return the oauth 2 service
     */
    DtOAuth2Service getOauth2Service();

    /**
     * agent service
     * @return service
     */
    DtAgentService getAgentService();

    DtCorpConversationMessageService getCorpConversationMsgService();
}
