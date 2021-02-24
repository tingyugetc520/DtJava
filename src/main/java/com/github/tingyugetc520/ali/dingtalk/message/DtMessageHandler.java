package com.github.tingyugetc520.ali.dingtalk.message;

import com.github.tingyugetc520.ali.dingtalk.api.DtService;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtEventMessage;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;

import java.util.Map;

/**
 * 处理推送消息的处理器接口
 */
public interface DtMessageHandler {

    /**
     * Handle message.
     *
     * @param message      the message
     * @param context      上下文，如果handler或interceptor之间有信息要传递，可以用这个
     * @param dtService    the dt service
     * @return 处理消息，如果在异步规则里处理的话，可以返回true
     * @throws DtErrorException the error exception
     */
    Boolean handle(DtEventMessage message,
                   Map<String, Object> context,
                   DtService dtService) throws DtErrorException;

}
