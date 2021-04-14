package com.github.tingyugetc520.ali.dingtalk.bean.user;

import com.github.tingyugetc520.ali.dingtalk.util.json.DtGsonBuilder;
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
 * unionId to userId info
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtUnionId2UserId implements Serializable {
    private static final long serialVersionUID = 618690417174457299L;

    private String userId;
    private Integer contactType;

    public static DtUnionId2UserId fromJson(String json) {
        return DtGsonBuilder.create().fromJson(json, DtUnionId2UserId.class);
    }

    public String toJson() {
        return DtGsonBuilder.create().toJson(this);
    }


}
