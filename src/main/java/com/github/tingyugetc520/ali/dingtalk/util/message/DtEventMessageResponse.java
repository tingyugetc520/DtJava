package com.github.tingyugetc520.ali.dingtalk.util.message;

import com.github.tingyugetc520.ali.dingtalk.config.DtConfigStorage;
import com.github.tingyugetc520.ali.dingtalk.util.crypto.DtCryptUtil;
import com.github.tingyugetc520.ali.dingtalk.util.json.DtGsonBuilder;

import java.util.Map;

public class DtEventMessageResponse {

    /**
     * 转换成加密的json格式.
     */
    public static String toEncryptedJson(DtConfigStorage configStorage, boolean success) {
        DtCryptUtil cryptUtil = new DtCryptUtil(configStorage);

        Map<String, String> encryptedMap;
        if (success) {
            encryptedMap = cryptUtil.getEncryptedMap("success");
        } else {
            encryptedMap = cryptUtil.getEncryptedMap("error");
        }
        return DtGsonBuilder.create().toJson(encryptedMap);
    }

}
