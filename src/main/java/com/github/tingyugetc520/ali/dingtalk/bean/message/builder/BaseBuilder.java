package com.github.tingyugetc520.ali.dingtalk.bean.message.builder;

import com.github.tingyugetc520.ali.dingtalk.bean.message.DtMessage;

import java.util.List;

public class BaseBuilder<T> {
  protected Long agentId;
  protected List<String> userIds;
  protected List<Long> departIds;
  protected Boolean toAllUser;
  protected String msgType;

  public T agentId(Long agentId) {
    this.agentId = agentId;
    return (T) this;
  }

  public T userIds(List<String> userIds) {
    this.userIds = userIds;
    return (T) this;
  }

  public T departIds(List<Long> departIds) {
    this.departIds = departIds;
    return (T) this;
  }

  public T toAllUser(Boolean toAllUser) {
    this.toAllUser = toAllUser;
    return (T) this;
  }

  public DtMessage build() {
    DtMessage m = new DtMessage();
    m.setMsgType(this.msgType);
    return m;
  }

}
