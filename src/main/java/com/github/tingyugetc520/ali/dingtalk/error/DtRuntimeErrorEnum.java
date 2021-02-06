package com.github.tingyugetc520.ali.dingtalk.error;

import lombok.Getter;

/**
 * dt异常枚举
 */
@Getter
public enum DtRuntimeErrorEnum {
    /**
     *
     */
    DT_HTTP_CALL_FAILED(400, "请求钉钉接口异常，请检查地址是否正确"),
    ;

    private int code;
    private String msg;

    DtRuntimeErrorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 通过错误代码查找其中文含义..
     */
    public static String findMsgByCode(int code) {
        for (DtRuntimeErrorEnum value : DtRuntimeErrorEnum.values()) {
            if (value.code == code) {
                return value.msg;
            }
        }

        return null;
    }
}
