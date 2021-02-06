package com.github.tingyugetc520.ali.dingtalk.api;

import com.github.tingyugetc520.ali.dingtalk.bean.agent.DtAgentAuthScope;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;

import java.util.List;

/**
 * 应用相关
 */
public interface DtAgentService {
  /**
   * 获取应用的通讯录权限范围
   * 详情请见: https://ding-doc.dingtalk.com/document/app/obtain-corpsecret-authorization-scope
   * @return auth scope
   */
  DtAgentAuthScope getAuthScope() throws DtErrorException;

}
