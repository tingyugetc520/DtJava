package com.github.tingyugetc520.ali.dingtalk.bean.message;

import com.github.tingyugetc520.ali.dingtalk.util.json.DtGsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 *
 */
@Data
public class DtCorpConversationMsgSendResult implements Serializable {
  private static final long serialVersionUID = 916455987193190004L;

  @SerializedName("errcode")
  private Integer errCode;

  @SerializedName("errmsg")
  private String errMsg;

  @SerializedName("request_id")
  private String requestId;

  @SerializedName("task_id")
  private String taskId;

  public static DtCorpConversationMsgSendResult fromJson(String json) {
    return DtGsonBuilder.create().fromJson(json, DtCorpConversationMsgSendResult.class);
  }

}
