package com.github.tingyugetc520.ali.dingtalk.bean.message;

import com.github.tingyugetc520.ali.dingtalk.config.DtConfigStorage;
import com.github.tingyugetc520.ali.dingtalk.util.json.DtGsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class DtEventOutMessage implements Serializable {
    private static final long serialVersionUID = -2082278303476631708L;

    @SerializedName("msg_signature")
    private String msgSignature;
    private Long timestamp;
    private String nonce;
    private String encrypt;

    public String toJson() {
        return DtGsonBuilder.create().toJson(this);
    }

    /**
     * 转换成加密的xml格式.
     */
    public String toEncryptedJson(DtConfigStorage configStorage) {
        
        return toJson();
    }

}
