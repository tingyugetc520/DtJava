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

/**
 * 用户信息.
 */
@Deprecated
@Data
@Accessors(chain = true)
public class DtUserV2 implements Serializable {
    private static final long serialVersionUID = -5696099236344075582L;

    private String userId;
    private String unionId;
    private String name;
    private String avatar;
    private String mobile;
    private Boolean hideMobile;
    private String telephone;
    private String jobNumber;
    private String title;
    private String email;
    private String workPlace;
    private String remark;
    /**
     * 所属部门ID列表
     */
    private Long[] departIds;
    /**
     * 员工在对应的部门中的排序
     */
    private List<DepartOrder> orders;
    private String extension;
    private Date hiredDate;
    private Boolean active;
    private Boolean realAuthed;
    private Boolean senior;
    private Boolean admin;
    private Boolean boss;

    public static DtUserV2 fromJson(String json) {
        return DtGsonBuilder.create().fromJson(json, DtUserV2.class);
    }

    public String toJson() {
        return DtGsonBuilder.create().toJson(this);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DepartOrder {
        private Long departId;
        private Long order;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DepartLeader {
        private Long departId;
        private Boolean leader;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserRole {
        private Long id;
        private String name;
        private String groupName;
    }

}
