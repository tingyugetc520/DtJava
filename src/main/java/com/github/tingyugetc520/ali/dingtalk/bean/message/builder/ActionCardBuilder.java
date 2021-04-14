package com.github.tingyugetc520.ali.dingtalk.bean.message.builder;

import com.github.tingyugetc520.ali.dingtalk.bean.message.DtMessage;
import com.github.tingyugetc520.ali.dingtalk.constant.DtConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 */
public class ActionCardBuilder extends BaseBuilder<ActionCardBuilder> {
  private String title;
  private String markdown;

  private String singleTitle;
  private String singleUrl;

  private String btnOrientation;
  private List<CardBtn> btnList;

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

    m.setBtnOrientation(this.btnOrientation);
    m.setBtnList(this.btnList);
    return m;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class CardBtn {
    private String title;
    private String actionUrl;
  }
}
