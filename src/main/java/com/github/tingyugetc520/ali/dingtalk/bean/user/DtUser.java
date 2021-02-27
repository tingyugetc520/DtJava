package com.github.tingyugetc520.ali.dingtalk.bean.user;

import com.github.tingyugetc520.ali.dingtalk.util.json.DtGsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户信息.
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtUser implements Serializable {
    private static final long serialVersionUID = -5696099236344075582L;

    @SerializedName("userid")
    private String userId;
    @SerializedName("unionid")
    private String unionId;

    private String managerUserId;
    private Date hiredDate;
    private String tel;

    private String remark;
    private String workPlace;

    private String name;
    private String position;
    private String mobile;
    private String stateCode;
    private String email;
    /**
     * 员工企业邮箱
     */
    private String orgEmail;

    private Boolean isSenior;
    @SerializedName("jobnumber")
    private String jobNumber;
    private Boolean active;
    private String avatar;
    /**
     * 扩展属性，可以设置多种属性
     */
    @SerializedName("extattr")
    private Map<String, String> extension;

    private List<Role> roles;

    @SerializedName("department")
    private List<Long> departIds;
//    @SerializedName("orderInDepts")
//    private Map<Long, Long> departOrders;

    private Boolean isAdmin;
//    @SerializedName("isLeaderInDepts")
//    private Map<Long, Boolean> isLeaderInDept;

    private Boolean isHide;
    private Boolean isBoss;
    private Boolean realAuthed;


    public static DtUser fromJson(String json) {
        return DtGsonBuilder.create().fromJson(json, DtUser.class);
    }

    public String toJson() {
        return DtGsonBuilder.create().toJson(this);
    }

    @Data
    public static class Role {
        private Long id;
        private String name;
        private Integer type;
        private String groupName;
    }

}
