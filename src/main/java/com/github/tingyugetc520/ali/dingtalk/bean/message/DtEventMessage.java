package com.github.tingyugetc520.ali.dingtalk.bean.message;

import com.github.tingyugetc520.ali.dingtalk.config.DtConfigStorage;
import com.github.tingyugetc520.ali.dingtalk.error.DtRuntimeException;
import com.github.tingyugetc520.ali.dingtalk.util.crypto.DtCryptUtil;
import com.github.tingyugetc520.ali.dingtalk.util.json.DtGsonBuilder;
import com.github.tingyugetc520.ali.dingtalk.util.json.GsonHelper;
import com.github.tingyugetc520.ali.dingtalk.util.json.GsonParser;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Data
@Slf4j
public class DtEventMessage implements Serializable {
    private static final long serialVersionUID = 5202339097541307818L;

    /**
     * 推送过来的属性和值的map.
     */
    private Map<String, Object> allFieldsMap;

    @SerializedName("CorpId")
    private String corpId;

    @SerializedName("EventType")
    private String eventType;

    @SerializedName("TimeStamp")
    private Long timeStamp;

    protected static DtEventMessage fromJson(String json) {
        DtEventMessage message = DtGsonBuilder.create().fromJson(json, DtEventMessage.class);
        message.setAllFieldsMap(DtGsonBuilder.create().fromJson(json, new TypeToken<Map<String, Object>>() {}.getType()));
        return message;
    }

    protected static DtEventMessage fromJson(InputStream is) {
        JsonReader reader = new JsonReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        return DtGsonBuilder.create().fromJson(reader, DtEventMessage.class);
    }

    /**
     * 从加密字符串转换.
     */
    public static DtEventMessage fromEncrypt(String encrypt, DtConfigStorage configStorage,
                                                   String timestamp, String nonce, String msgSignature) {
        DtCryptUtil cryptUtil = new DtCryptUtil(configStorage);
        String plainText = cryptUtil.getDecryptMsg(msgSignature, timestamp, nonce, encrypt);
        log.debug("解密后的原始json消息内容：{}", plainText);
        return fromJson(plainText);
    }

    public static DtEventMessage fromEncryptedJson(String encryptedJson, DtConfigStorage configStorage,
                                                   String timestamp, String nonce, String msgSignature) {
        JsonObject jsonObject = GsonParser.parse(encryptedJson);
        String encrypt = GsonHelper.getString(jsonObject, "encrypt");
        return fromEncrypt(encrypt, configStorage, timestamp, nonce, msgSignature);
    }

    public static DtEventMessage fromEncryptedJson(InputStream is, DtConfigStorage configStorage,
                                                  String timestamp, String nonce, String msgSignature) {
        try {
            return fromEncryptedJson(IOUtils.toString(is, StandardCharsets.UTF_8), configStorage, timestamp, nonce, msgSignature);
        } catch (IOException e) {
            throw new DtRuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return DtGsonBuilder.create().toJson(this);
    }

}
