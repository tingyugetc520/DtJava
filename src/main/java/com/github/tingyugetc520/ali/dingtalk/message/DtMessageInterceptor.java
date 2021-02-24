package com.github.tingyugetc520.ali.dingtalk.message;

import com.github.tingyugetc520.ali.dingtalk.api.DtService;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtEventMessage;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;

import java.util.Map;

/**
 * 消息拦截器，可以用来做验证
 */
public interface DtMessageInterceptor {

    /**
     * 拦截消息
     *
     * @param message      the message
     * @param context      上下文，如果handler或interceptor之间有信息要传递，可以用这个
     * @param dtService    the dt service
     * @return true代表OK ，false代表不OK
     * @throws DtErrorException the error exception
     */
    boolean intercept(DtEventMessage message,
                      Map<String, Object> context,
                      DtService dtService) throws DtErrorException;

}
