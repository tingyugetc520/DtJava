package com.github.tingyugetc520.ali.dingtalk.bean;

import com.github.tingyugetc520.ali.dingtalk.util.json.DtGsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * access token
 */
@Data
@Accessors(chain = true)
public class DtAccessToken implements Serializable {
    private static final long serialVersionUID = -944200774978928518L;

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("expires_in")
    private int expiresIn = -1;

    public static DtAccessToken fromJson(String json) {
        return DtGsonBuilder.create().fromJson(json, DtAccessToken.class);
    }
}
