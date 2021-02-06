package com.github.tingyugetc520.ali.dingtalk.bean.message.builder;

import com.github.tingyugetc520.ali.dingtalk.bean.message.DtMessage;
import com.github.tingyugetc520.ali.dingtalk.constant.DtConstant;

/**
 *
 */
public final class LinkBuilder extends BaseBuilder<LinkBuilder> {
  private String messageUrl;
  private String picUrl;
  private String title;
  private String text;

  public LinkBuilder() {
    this.msgType = DtConstant.AppMsgType.LINK;
  }

  public LinkBuilder messageUrl(String messageUrl) {
    this.messageUrl = messageUrl;
    return this;
  }

  public LinkBuilder picUrl(String picUrl) {
    this.picUrl = picUrl;
    return this;
  }

  public LinkBuilder title(String title) {
    this.title = title;
    return this;
  }

  public LinkBuilder text(String text) {
    this.text = text;
    return this;
  }

  @Override
  public DtMessage build() {
    DtMessage m = super.build();
    m.setMessageUrl(this.messageUrl);
    m.setPicUrl(this.picUrl);
    m.setTitle(this.title);
    m.setText(this.text);
    return m;
  }
}
