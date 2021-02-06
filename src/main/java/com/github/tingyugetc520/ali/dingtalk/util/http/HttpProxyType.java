package com.github.tingyugetc520.ali.dingtalk.util.http;

/**
 * Http Proxy types
 */
public enum HttpProxyType {
    /**
     * none
     */
    NONE(0),
    /**
     * 反向代理
     */
    REVERSE(1),
    /**
     * 正向代理
     */
    FORWARD(2),
    ;

    private int code;

    HttpProxyType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    /**
     * 通过错误代码查找其中文含义..
     */
    public static HttpProxyType findByCode(int code) {
        for (HttpProxyType value : HttpProxyType.values()) {
            if (value.code == code) {
                return value;
            }
        }

        return null;
    }
}