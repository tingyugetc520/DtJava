package com.github.tingyugetc520.ali.dingtalk.bean.message;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 企业工作通知消息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtCorpConversationMessage implements Serializable {
    private static final long serialVersionUID = 5492729923265382806L;

    private Long agentId;
    private List<String> userIds;
    private List<Long> departIds;
    private Boolean toAllUser;
    private DtMessage msg;

    public String toJson() {
        JsonObject messageJson = new JsonObject();
        if (this.getAgentId() != null) {
            messageJson.addProperty("agent_id", this.getAgentId());
        }

        if (CollectionUtils.isNotEmpty(this.userIds)) {
            messageJson.addProperty("userid_list", StringUtils.join(this.userIds, ","));
        }

        if (CollectionUtils.isNotEmpty(this.departIds)) {
            messageJson.addProperty("dept_id_list", StringUtils.join(this.departIds, ","));
        }

        if (Objects.nonNull(this.toAllUser)) {
            messageJson.addProperty("to_all_user", this.toAllUser);
        }

        if (msg != null) {
            messageJson.add("msg", msg.toJsonObject());
        }

        return messageJson.toString();
    }

}
