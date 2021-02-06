package com.github.tingyugetc520.ali.dingtalk.api.impl;

import com.github.tingyugetc520.ali.dingtalk.api.DtCorpConversationMessageService;
import com.github.tingyugetc520.ali.dingtalk.api.DtService;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtCorpConversationMessage;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtCorpConversationMsgSendResult;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;
import lombok.RequiredArgsConstructor;

import static com.github.tingyugetc520.ali.dingtalk.constant.DtApiPathConstant.Message.*;

/**
 * 应用相关
 */
@RequiredArgsConstructor
public class DtCorpConversationMessageServiceImpl implements DtCorpConversationMessageService {
    private final DtService mainService;

    @Override
    public DtCorpConversationMsgSendResult send(DtCorpConversationMessage message) throws DtErrorException {
        final String url = this.mainService.getDtConfigStorage().getApiUrl(AppCordConversation.MESSAGE_SEND);
        return DtCorpConversationMsgSendResult.fromJson(this.mainService.post(url, message.toJson()));
    }

}
