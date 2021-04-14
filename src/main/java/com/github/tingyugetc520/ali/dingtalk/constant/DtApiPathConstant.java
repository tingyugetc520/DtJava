package com.github.tingyugetc520.ali.dingtalk.constant;

import lombok.experimental.UtilityClass;

/**
 * api地址常量类
 */
public final class DtApiPathConstant {
    public static final String DEFAULT_DT_BASE_URL = "https://oapi.dingtalk.com";

    public static final String GET_JSAPI_TICKET = "/get_jsapi_ticket";
    public static final String GET_TOKEN = "/gettoken?appkey=%s&appsecret=%s";

    /**
     * 消息通知相关接口
     */
    @UtilityClass
    public static class Message {
        /**
         * 工作通知
         */
        public static class AppCordConversation {
            public static final String MESSAGE_SEND = "/topapi/message/corpconversation/asyncsend_v2";
        }
    }

    /**
     * 免登
     */
    @UtilityClass
    public static class OAuth2 {
        public static final String GET_USER_INFO = "/user/getuserinfo?code=%s";
    }

    /**
     * 部门管理
     */
    @UtilityClass
    public static class Department {
        public static final String DEPARTMENT_CREATE = "/department/create";
        public static final String DEPARTMENT_UPDATE = "/department/update";
        public static final String DEPARTMENT_DELETE = "/department/delete?id=%d";
        public static final String DEPARTMENT_DETAIL = "/department/detail?id=%d";
        public static final String DEPARTMENT_LIST = "/department/list";
    }

    /**
     * 用户管理
     */
    @UtilityClass
    public static class User {
        public static final String USER_CREATE = "/user/create";
        public static final String USER_UPDATE = "/user/update";
        public static final String USER_DELETE = "/user/delete?userid=";
        public static final String USER_GET = "/user/get?userid=";
        public static final String USER_ID_GET = "/user/get_by_mobile?mobile=";
        public static final String USER_ID_LIST = "/user/getdeptmember?deptId=";
        public static final String USER_LIST = "/user/listbypage?department_id=%d&offset=%d&size=%d";
        public static final String USER_SIMPLE_LIST = "/user/simplelist?department_id=%d&offset=%d&size=%d";
        public static final String UNION_ID_2_USER_ID = "/user/getuseridbyunionid?unionid=";
    }

    @UtilityClass
    public static class Agent {
        public static final String AGENT_AUTH_SCOPE = "/auth/scopes";
    }

}
