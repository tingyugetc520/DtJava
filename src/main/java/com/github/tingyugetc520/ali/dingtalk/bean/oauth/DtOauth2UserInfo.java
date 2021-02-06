package com.github.tingyugetc520.ali.dingtalk.bean.oauth;

import com.github.tingyugetc520.ali.dingtalk.util.json.DtGsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用oauth2获取用户信息的结果类
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtOauth2UserInfo implements Serializable {
    private static final long serialVersionUID = 4259473057696770084L;

    @SerializedName("userid")
    private String userId;
    private String name;
    private String deviceId;
    /**
     * 是否是管理员
     */
    @SerializedName("is_sys")
    private Boolean isSys;
    /**
     * 级别
     */
    @SerializedName("sys_level")
    private Integer sysLevel;


    public static DtOauth2UserInfo fromJson(String json) {
        return DtGsonBuilder.create().fromJson(json, DtOauth2UserInfo.class);
    }
}
