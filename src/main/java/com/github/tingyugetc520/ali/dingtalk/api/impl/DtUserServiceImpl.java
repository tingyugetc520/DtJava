package com.github.tingyugetc520.ali.dingtalk.api.impl;

import com.github.tingyugetc520.ali.dingtalk.api.DtService;
import com.github.tingyugetc520.ali.dingtalk.api.DtUserService;
import com.github.tingyugetc520.ali.dingtalk.bean.user.DtUser;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;
import com.github.tingyugetc520.ali.dingtalk.util.json.DtGsonBuilder;
import com.github.tingyugetc520.ali.dingtalk.util.json.GsonParser;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.github.tingyugetc520.ali.dingtalk.constant.DtApiPathConstant.User.*;

@RequiredArgsConstructor
public class DtUserServiceImpl implements DtUserService {
    private final DtService mainService;

    @Override
    public DtUser getById(String userId) throws DtErrorException {
        String url = this.mainService.getDtConfigStorage().getApiUrl(USER_GET + userId);
        String responseContent = this.mainService.get(url, null);
        return DtUser.fromJson(responseContent);
    }

    @Override
    public List<DtUser> listByDepartment(Long departId, Integer offset, Integer size, String order) throws DtErrorException {
        String params = "";
        if (order != null) {
            params += "&order=" + order;
        }

        String url = String.format(this.mainService.getDtConfigStorage().getApiUrl(USER_LIST), departId, offset, size);
        String responseContent = this.mainService.get(url, params);
        JsonObject jsonObject = GsonParser.parse(responseContent);
        return DtGsonBuilder.create()
                .fromJson(
                        jsonObject.get("userlist"),
                        new TypeToken<List<DtUser>>() {}.getType()
                );
    }

    @Override
    public List<DtUser> listSimpleByDepartment(Long departId, Integer offset, Integer size, String order) throws DtErrorException {
        String params = "";
        if (order != null) {
            params += "&order=" + order;
        }

        String url = String.format(this.mainService.getDtConfigStorage().getApiUrl(USER_SIMPLE_LIST), departId, offset, size);
        String responseContent = this.mainService.get(url, params);
        JsonObject tmpJson = GsonParser.parse(responseContent);
        return DtGsonBuilder.create()
                .fromJson(
                        tmpJson.get("userlist"),
                        new TypeToken<List<DtUser>>() {}.getType()
                );
    }

}
