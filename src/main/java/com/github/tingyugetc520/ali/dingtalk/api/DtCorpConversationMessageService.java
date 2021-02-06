package com.github.tingyugetc520.ali.dingtalk.api;

import com.github.tingyugetc520.ali.dingtalk.bean.message.DtCorpConversationMessage;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtCorpConversationMsgSendResult;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;

/**
 *
 */
public interface DtCorpConversationMessageService {

  DtCorpConversationMsgSendResult send(DtCorpConversationMessage message) throws DtErrorException;

}
