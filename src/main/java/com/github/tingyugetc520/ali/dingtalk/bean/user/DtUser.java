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

/**
 * 用户信息.
 */
@Data
@Accessors(chain = true)
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
   * 是一个json字符串，支持转义的，在这里不特殊处理，交由业务处理
   */
  @SerializedName("extattr")
  private String extension;
//  private List<Roles> roles;

  @SerializedName("department")
  private List<Long> departIds;
//  @SerializedName("orderInDepts")
//  private Map<Long, Long> departOrders;

  private Boolean isAdmin;
//  @SerializedName("isLeaderInDepts")
//  private Map<Long, Boolean> isLeaderInDept;

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
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Roles {
    private Long id;
    private String name;
    private String groupName;
  }

}
