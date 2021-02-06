package com.github.tingyugetc520.ali.dingtalk.api.impl;

import com.github.tingyugetc520.ali.dingtalk.api.DtAgentService;
import com.github.tingyugetc520.ali.dingtalk.api.DtService;
import com.github.tingyugetc520.ali.dingtalk.bean.agent.DtAgentAuthScope;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;
import lombok.RequiredArgsConstructor;

import static com.github.tingyugetc520.ali.dingtalk.constant.DtApiPathConstant.Agent.*;


/**
 * 应用相关
 */
@RequiredArgsConstructor
public class DtAgentServiceImpl implements DtAgentService {
    private final DtService mainService;

    @Override
    public DtAgentAuthScope getAuthScope() throws DtErrorException {
        final String url = this.mainService.getDtConfigStorage().getApiUrl(AGENT_AUTH_SCOPE);
        return DtAgentAuthScope.fromJson(this.mainService.get(url, null));
    }

}
