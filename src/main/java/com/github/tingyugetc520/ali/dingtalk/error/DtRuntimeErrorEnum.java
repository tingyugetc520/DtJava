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

    /**
     *
     */
    ENCRYPTION_PLAINTEXT_ILLEGAL(900001, "加密明文文本非法"),
    ENCRYPTION_TIMESTAMP_ILLEGAL(900002, "加密时间戳参数非法"),
    ENCRYPTION_NONCE_ILLEGAL(900003, "加密随机字符串参数非法"),
    AES_KEY_ILLEGAL(900004, "签名不匹配"),
    SIGNATURE_NOT_MATCH(900005, "签名计算失败"),
    COMPUTE_SIGNATURE_ERROR(900006, "不合法的aes key"),
    COMPUTE_ENCRYPT_TEXT_ERROR(900007, "计算加密文字错误"),
    COMPUTE_DECRYPT_TEXT_ERROR(900008, "计算解密文字错误"),
    COMPUTE_DECRYPT_TEXT_LENGTH_ERROR(900009, "计算解密文字长度不匹配"),
    COMPUTE_DECRYPT_TEXT_CORPID_ERROR(900010, "计算解密文字corpid不匹配"),
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
