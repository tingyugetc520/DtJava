package com.github.tingyugetc520.ali.dingtalk.bean.message.builder;

import com.github.tingyugetc520.ali.dingtalk.bean.message.DtMessage;
import com.github.tingyugetc520.ali.dingtalk.constant.DtConstant;

/**
 *
 */
public final class VoiceBuilder extends BaseBuilder<VoiceBuilder> {
  private String mediaId;
  private Integer duration;

  public VoiceBuilder() {
    this.msgType = DtConstant.AppMsgType.VOICE;
  }

  public VoiceBuilder mediaId(String mediaId) {
    this.mediaId = mediaId;
    return this;
  }

  public VoiceBuilder duration(Integer duration) {
    this.duration = duration;
    return this;
  }

  @Override
  public DtMessage build() {
    DtMessage m = super.build();
    m.setMediaId(this.mediaId);
    return m;
  }
}
