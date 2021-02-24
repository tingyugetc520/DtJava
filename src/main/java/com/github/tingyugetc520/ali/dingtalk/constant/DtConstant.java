package com.github.tingyugetc520.ali.dingtalk.constant;

import com.github.tingyugetc520.ali.dingtalk.util.DtConstantUtils;
import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
         * check url
         */
        public static String CHECK_URL = "check_url";

        /**
         * 通讯录变更事件
         */
        public static class ChangeContact {
            /**
             * 用户变更-通讯录用户增加
             */
            public static String USER_ADD_ORG = "user_add_org";
            /**
             * 用户变更-通讯录用户更改
             */
            public static String USER_MODIFY_ORG = "user_modify_org";
            /**
             * 用户变更-通讯录用户离职
             */
            public static String USER_LEAVE_ORG = "user_leave_org";
            /**
             * 用户变更-加入企业后用户激活
             */
            public static String USER_ACTIVE_ORG = "user_active_org";
            /**
             * 用户变更-通讯录用户被设为管理员
             */
            public static String ORG_ADMIN_ADD = "org_admin_add";
            /**
             * 用户变更-通讯录用户被取消设置管理员
             */
            public static String ORG_ADMIN_REMOVE = "org_admin_remove";
            /**
             * 部门变更-通讯录企业部门创建
             */
            public static String ORG_DEPT_CREATE = "org_dept_create";
            /**
             * 部门变更-通讯录企业部门修改
             */
            public static String ORG_DEPT_MODIFY = "org_dept_modify";
            /**
             * 部门变更-通讯录企业部门删除
             */
            public static String ORG_DEPT_REMOVE = "org_dept_remove";
            /**
             * 企业信息变更-企业被解散
             */
            public static String ORG_REMOVE = "org_remove";
            /**
             * 企业信息变更-企业信息发生变更
             */
            public static String ORG_CHANGE = "org_change";
            /**
             * 角色变更-员工角色信息发生变更
             */
            public static String LABEL_USER_CHANGE = "label_user_change";
            /**
             * 角色变更-增加角色或者角色组
             */
            public static String LABEL_CONF_ADD = "label_conf_add";
            /**
             * 角色变更-删除角色或者角色组
             */
            public static String LABEL_CONF_DEL = "label_conf_del";
            /**
             * 角色变更-修改角色或者角色组
             */
            public static String LABEL_CONF_MODIFY = "label_conf_modify";

        }
        public static List<String> ChangeContactGroup = DtConstantUtils.getEventTypeGroup(ChangeContact.class);
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
