package com.github.tingyugetc520.ali.dingtalk.bean.message;

import com.github.tingyugetc520.ali.dingtalk.config.DtConfigStorage;
import com.github.tingyugetc520.ali.dingtalk.util.crypto.DtCryptUtil;
import com.github.tingyugetc520.ali.dingtalk.util.json.DtGsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Map;

@Builder
@Data
@Slf4j
public class DtEventOutMessage implements Serializable {
    private static final long serialVersionUID = 1187214481256075303L;

    /**
     * map
     */
    private Map<String, String> allFieldsMap;

    @SerializedName("msg_signature")
    private String msgSignature;

    @SerializedName("timeStamp")
    private String timestamp;

    private String nonce;

    private String encrypt;

    /**
     * 转换成加密的json格式
     */
    public String toEncryptedJson() {
        return DtGsonBuilder.create().toJson(this);
    }

    public static DtEventOutMessage toEncrypted(DtConfigStorage configStorage, Boolean success) {
        DtCryptUtil cryptUtil = new DtCryptUtil(configStorage);

        Map<String, String> encryptedMap;
        if (Boolean.TRUE.equals(success)) {
            encryptedMap = cryptUtil.getEncryptedMap("success");
        } else {
            encryptedMap = cryptUtil.getEncryptedMap("error");
        }
        return DtEventOutMessage.builder()
                .allFieldsMap(encryptedMap)
                .msgSignature(encryptedMap.get("msg_signature"))
                .timestamp(encryptedMap.get("timeStamp"))
                .nonce(encryptedMap.get("nonce"))
                .encrypt(encryptedMap.get("encrypt"))
                .build();
    }

}
