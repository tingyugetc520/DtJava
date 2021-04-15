package com.github.tingyugetc520.ali.dingtalk.bean.message;

import com.github.tingyugetc520.ali.dingtalk.util.json.DtGsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtCorpConversationRecall implements Serializable {
    private static final long serialVersionUID = -1414188116769589593L;

    @SerializedName("agent_id")
    private Long agentId;
    @SerializedName("msg_task_id")
    private Long taskId;

    public String toJson() {
        return DtGsonBuilder.create().toJson(this);
    }

}
