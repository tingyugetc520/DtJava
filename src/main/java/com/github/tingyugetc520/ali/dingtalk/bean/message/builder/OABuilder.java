package com.github.tingyugetc520.ali.dingtalk.bean.message.builder;

import com.github.tingyugetc520.ali.dingtalk.bean.message.DtMessage;
import com.github.tingyugetc520.ali.dingtalk.constant.DtConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * oa消息
 */
public class OABuilder extends BaseBuilder<OABuilder> {
  private String messageUrl;
  private OAHead oaHead;
  private OABody oaBody;

  public OABuilder() {
    this.msgType = DtConstant.AppMsgType.OA;
  }

  public OABuilder messageUrl(String messageUrl) {
    this.messageUrl = messageUrl;
    return this;
  }

  public OABuilder oaHead(OAHead oaHead) {
    this.oaHead = oaHead;
    return this;
  }

  public OABuilder messageUrl(OABody oaBody) {
    this.oaBody = oaBody;
    return this;
  }

  @Override
  public DtMessage build() {
    DtMessage m = super.build();
    m.setMessageUrl(this.messageUrl);
    m.setOaHead(this.oaHead);
    m.setOaBody(this.oaBody);
    return m;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class OAHead {
    private String bgColor;
    private String text;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class OABody {
    private String title;
    private List<OABodyForm> form;
    private OABodyRich rich;
    private String content;
    private String image;
    private String fileCount;
    private String author;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class OABodyForm {
    private String key;
    private String value;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class OABodyRich {
    private String num;
    private String unit;
  }
}
