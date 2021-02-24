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
     * 处理消息，当消息处理成功时可以返回true，是被返回false
     * 未处理消息则可以返回null，比如仅仅是记录了一下日志而已
     *  此时不论是返回false还是true都会有问题，返回true则会认为消息处理成功返回false则会认为消息处理失败，然而消息实际未处理
     *  此时返回null，但依然会返回钉钉失败的响应，在后续业务方可以拉取失败消息来重新处理
     *
     * @param message      the message
     * @param context      上下文，如果handler或interceptor之间有信息要传递，可以用这个
     * @param dtService    the dt service
     * @return 处理消息的结果
     * @throws DtErrorException the error exception
     */
    Boolean handle(DtEventMessage message,
                   Map<String, Object> context,
                   DtService dtService) throws DtErrorException;

}
