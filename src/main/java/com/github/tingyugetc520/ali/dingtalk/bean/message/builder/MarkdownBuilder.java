package com.github.tingyugetc520.ali.dingtalk.bean.message.builder;

import com.github.tingyugetc520.ali.dingtalk.bean.message.DtMessage;
import com.github.tingyugetc520.ali.dingtalk.constant.DtConstant;

/**
 *
 */
public class MarkdownBuilder extends BaseBuilder<MarkdownBuilder> {
  private String title;
  private String text;

  public MarkdownBuilder() {
    this.msgType = DtConstant.AppMsgType.MARKDOWN;
  }

  public MarkdownBuilder content(String title) {
    this.title = title;
    return this;
  }

  public MarkdownBuilder text(String text) {
    this.text = text;
    return this;
  }

  @Override
  public DtMessage build() {
    DtMessage m = super.build();
    m.setTitle(this.title);
    m.setText(this.text);
    return m;
  }
}
