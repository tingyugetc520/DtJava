package com.github.tingyugetc520.ali.dingtalk.api;

import com.github.tingyugetc520.ali.dingtalk.bean.message.DtCorpConversationMessage;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtCorpConversationMsgSendResult;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtCorpConversationRecall;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtCorpConversationStatusBarUpdate;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;

/**
 * 消息通知-工作通知
 */
public interface DtCorpConversationMessageService {

  /**
   * 发送工作通知
   * https://developers.dingtalk.com/document/app/asynchronous-sending-of-enterprise-session-messages
   *
   * @param message msg
   * @return send result
   * @throws DtErrorException error
   */
  DtCorpConversationMsgSendResult send(DtCorpConversationMessage message) throws DtErrorException;

  /**
   * https://developers.dingtalk.com/document/app/update-work-notification-status-bar
   * 更新工作通知状态栏
   *
   * @param update param
   * @return requestId
   * @throws DtErrorException error
   */
  String updateOaStatusBar(DtCorpConversationStatusBarUpdate update) throws DtErrorException;

  /**
   * 撤回工作通知
   * https://developers.dingtalk.com/document/app/notification-of-work-withdrawal
   *
   * @param recall recall msg
   * @throws DtErrorException error
   */
  void recall(DtCorpConversationRecall recall) throws DtErrorException;
}
