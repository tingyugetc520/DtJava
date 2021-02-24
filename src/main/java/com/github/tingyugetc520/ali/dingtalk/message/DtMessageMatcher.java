package com.github.tingyugetc520.ali.dingtalk.message;

import com.github.tingyugetc520.ali.dingtalk.bean.message.DtEventMessage;

/**
 * 消息匹配器，用在消息路由的时候
 */
public interface DtMessageMatcher {

    /**
     * 消息是否匹配某种模式
     *
     * @param message the message
     * @return the boolean
     */
    boolean match(DtEventMessage message);

}
