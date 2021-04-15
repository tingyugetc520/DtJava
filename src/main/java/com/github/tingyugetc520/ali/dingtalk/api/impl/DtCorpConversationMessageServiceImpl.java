package com.github.tingyugetc520.ali.dingtalk.api.impl;

import com.github.tingyugetc520.ali.dingtalk.api.DtCorpConversationMessageService;
import com.github.tingyugetc520.ali.dingtalk.api.DtService;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtCorpConversationMessage;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtCorpConversationMsgSendResult;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtCorpConversationRecall;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtCorpConversationStatusBarUpdate;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;
import com.github.tingyugetc520.ali.dingtalk.util.json.GsonHelper;
import com.github.tingyugetc520.ali.dingtalk.util.json.GsonParser;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;

import static com.github.tingyugetc520.ali.dingtalk.constant.DtApiPathConstant.Message.*;

/**
 * 消息通知-工作通知
 */
@RequiredArgsConstructor
public class DtCorpConversationMessageServiceImpl implements DtCorpConversationMessageService {
    private final DtService mainService;

    @Override
    public DtCorpConversationMsgSendResult send(DtCorpConversationMessage message) throws DtErrorException {
        String url = this.mainService.getDtConfigStorage().getApiUrl(AppCordConversation.SEND);
        return DtCorpConversationMsgSendResult.fromJson(this.mainService.post(url, message.toJson()));
    }

    @Override
    public String updateOaStatusBar(DtCorpConversationStatusBarUpdate update) throws DtErrorException {
        String url = this.mainService.getDtConfigStorage().getApiUrl(AppCordConversation.STATUS_BAR_UPDATE);
        String res = this.mainService.post(url, update.toJson());

        JsonObject jsonObject = GsonParser.parse(res);
        return GsonHelper.getString(jsonObject, "request_id");
    }

    @Override
    public void recall(DtCorpConversationRecall recall) throws DtErrorException {
        String url = this.mainService.getDtConfigStorage().getApiUrl(AppCordConversation.RECALL);
        this.mainService.post(url, recall.toJson());
    }
}
