package com.github.tingyugetc520.ali.dingtalk.bean.department;

import com.github.tingyugetc520.ali.dingtalk.util.json.DtGsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * 部门.
 */
@Data
public class DtDepart implements Serializable {
  private static final long serialVersionUID = -5028321625140879571L;

  private Long id;
  private String name;
  @SerializedName("parentid")
  private Long parentId;
  private Long order;

  public static DtDepart fromJson(String json) {
    return DtGsonBuilder.create().fromJson(json, DtDepart.class);
  }

  public String toJson() {
    return DtGsonBuilder.create().toJson(this);
  }

}
