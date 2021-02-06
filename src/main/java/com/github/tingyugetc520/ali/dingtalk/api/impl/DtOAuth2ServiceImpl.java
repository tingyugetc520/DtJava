package com.github.tingyugetc520.ali.dingtalk.api.impl;

import com.github.tingyugetc520.ali.dingtalk.api.DtOAuth2Service;
import com.github.tingyugetc520.ali.dingtalk.api.DtService;
import com.github.tingyugetc520.ali.dingtalk.bean.oauth.DtOauth2UserInfo;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;
import com.github.tingyugetc520.ali.dingtalk.util.json.GsonHelper;
import com.github.tingyugetc520.ali.dingtalk.util.json.GsonParser;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;

import static com.github.tingyugetc520.ali.dingtalk.constant.DtApiPathConstant.OAuth2.*;

/**
 * oauth2相关接口实现类
 */
@RequiredArgsConstructor
public class DtOAuth2ServiceImpl implements DtOAuth2Service {
    private final DtService mainService;


    @Override
    public DtOauth2UserInfo getUserInfo(String code) throws DtErrorException {
        String responseText = this.mainService.get(String.format(this.mainService.getDtConfigStorage().getApiUrl(GET_USER_INFO), code), null);

        return DtOauth2UserInfo.fromJson(responseText);
    }

}
