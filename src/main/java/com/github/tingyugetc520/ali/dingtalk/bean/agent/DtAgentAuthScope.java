package com.github.tingyugetc520.ali.dingtalk.bean.agent;

import com.github.tingyugetc520.ali.dingtalk.util.json.DtGsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 应用通讯录权限信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtAgentAuthScope implements Serializable {
  private static final long serialVersionUID = 5002894979081127234L;

  @SerializedName("auth_user_field")
  private List<String> authUserField;

  @SerializedName("auth_org_scopes")
  private AuthOrgScopes authOrgScopes;

  public static DtAgentAuthScope fromJson(String json) {
    return DtGsonBuilder.create().fromJson(json, DtAgentAuthScope.class);
  }

  public String toJson() {
    return DtGsonBuilder.create().toJson(this);
  }

  @Data
  public static class AuthOrgScopes implements Serializable {
    private static final long serialVersionUID = 8801100463558788565L;
    @SerializedName("authed_user")
    private List<String> users;
    @SerializedName("authed_dept")
    private List<Long> partyIds;
  }
}
