package com.github.tingyugetc520.ali.dingtalk.error;

import com.github.tingyugetc520.ali.dingtalk.util.json.DtGsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 钉钉错误码.
 * 请阅读：https://ding-doc.dingtalk.com/document/app/server-api-error-codes-1
 */
@Data
@Builder
public class DtError implements Serializable {
    private static final long serialVersionUID = 7869786563361406291L;

    /**
     * 错误代码.
     */
    private int errorCode;

    /**
     * 错误信息.
     */
    private String errorMsg;

    private String json;

    public static DtError fromJson(String json) {
        final DtError error = DtGsonBuilder.create().fromJson(json, DtError.class);
//        if (error.getErrorCode() == 0) {
//            return error;
//        }

        // 钉钉的错误码文档特么是乱写的，还是要以实际返回为准
//        final String msg = DtErrorMsgEnum.findMsgByCode(error.getErrorCode());
//        if (msg != null) {
//            error.setErrorMsg(msg);
//        }

        return error;
    }

    @Override
    public String toString() {
        if (this.json == null) {
            return "错误代码：" + this.errorCode + ", 错误信息：" + this.errorMsg;
        }

        return "错误代码：" + this.errorCode + ", 错误信息：" + this.errorMsg + "，钉钉原始报文：" + this.json;
    }

}
