package com.github.tingyugetc520.ali.dingtalk.api.impl;

import com.github.tingyugetc520.ali.dingtalk.api.DtDepartmentService;
import com.github.tingyugetc520.ali.dingtalk.api.DtService;
import com.github.tingyugetc520.ali.dingtalk.bean.department.DtDepart;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;
import com.github.tingyugetc520.ali.dingtalk.util.json.DtGsonBuilder;
import com.github.tingyugetc520.ali.dingtalk.util.json.GsonParser;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.github.tingyugetc520.ali.dingtalk.constant.DtApiPathConstant.Department.*;

/**
 * 部门管理接口
 */
@RequiredArgsConstructor
public class DtDepartmentServiceImpl implements DtDepartmentService {
    private final DtService mainService;

    @Override
    public List<DtDepart> list(Long id) throws DtErrorException {
        return list(id, true);
    }

    @Override
    public List<DtDepart> list(Long id, Boolean fetchChild) throws DtErrorException {
        String params = "";
        if (id != null) {
            params += "&id=" + id;
        }
        if (fetchChild != null) {
            params += "&fetch_child=" + (fetchChild ? "true" : "false");
        }

        String url = this.mainService.getDtConfigStorage().getApiUrl(DEPARTMENT_LIST);
        String responseContent = this.mainService.get(url, params);
        JsonObject tmpJsonObject = GsonParser.parse(responseContent);
        return DtGsonBuilder.create()
                .fromJson(
                        tmpJsonObject.get("department"),
                        new TypeToken<List<DtDepart>>() {}.getType()
                );
    }
}
