package com.github.tingyugetc520.ali.dingtalk.bean.message.builder;

import com.github.tingyugetc520.ali.dingtalk.bean.message.DtMessage;
import com.github.tingyugetc520.ali.dingtalk.constant.DtConstant;

/**
 *
 */
public class ActionCardBuilder extends BaseBuilder<ActionCardBuilder> {
  private String title;
  private String markdown;
  private String singleTitle;
  private String singleUrl;

  public ActionCardBuilder() {
    this.msgType = DtConstant.AppMsgType.ACTIONCARD;
  }

  public ActionCardBuilder title(String title) {
    this.title = title;
    return this;
  }

  public ActionCardBuilder markdown(String markdown) {
    this.markdown = markdown;
    return this;
  }

  public ActionCardBuilder singleTitle(String singleTitle) {
    this.singleTitle = singleTitle;
    return this;
  }

  public ActionCardBuilder singleUrl(String singleUrl) {
    this.singleUrl = singleUrl;
    return this;
  }

  @Override
  public DtMessage build() {
    DtMessage m = super.build();
    m.setTitle(this.title);
    m.setMarkdown(this.markdown);
    m.setSingleTitle(this.singleTitle);
    m.setSingleUrl(this.singleUrl);
    return m;
  }
}
