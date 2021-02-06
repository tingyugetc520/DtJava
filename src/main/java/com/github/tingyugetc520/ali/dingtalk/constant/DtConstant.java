package com.github.tingyugetc520.ali.dingtalk.constant;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

import static com.github.tingyugetc520.ali.dingtalk.error.DtErrorMsgEnum.*;

/**
 * 钉钉常量
 */
public final class DtConstant {
    /**
     * access_token 相关错误代码
     *
     * 发生以下情况时尝试刷新access_token
     * 40001 获取access_token时AppSecret错误，或者access_token无效
     * 42001 access_token超时
     * 40014 不合法的access_token
     *
     */
    public static final List<Integer> ACCESS_TOKEN_ERROR_CODES = Arrays.asList(
            CODE_40001.getCode(), CODE_40014.getCode(), CODE_42001.getCode()
    );

    /**
     * 推送过来的事件类型
     */
    @UtilityClass
    public static class EventType {
        /**
         * 通讯录变更事件
         */
        public static class ChangeContact {
            /**
             * 通讯录用户增加
             */
            public static final String USER_ADD_ORG = "user_add_org";
        }
    }

    /**
     * 消息通知的消息类型.
     */
    @UtilityClass
    public static class AppMsgType {
        /**
         * 文本消息.
         */
        public static final String TEXT = "text";
        /**
         * 图片消息.
         */
        public static final String IMAGE = "image";
        /**
         * 语音消息.
         */
        public static final String VOICE = "voice";
        /**
         * 发送文件
         */
        public static final String FILE = "file";
        /**
         * 链接
         */
        public static final String LINK = "link";
        /**
         * oa消息
         */
        public static final String OA = "OA";
        /**
         * markdown消息.
         */
        public static final String MARKDOWN = "markdown";
        /**
         * 卡片消息
         */
        public static final String ACTIONCARD = "action_card";
    }

}
