package com.github.tingyugetc520.ali.dingtalk.api;

import com.github.tingyugetc520.ali.dingtalk.bean.oauth.DtOauth2UserInfo;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;

/**
 * OAuth2相关管理接口.
 */
public interface DtOAuth2Service {

  /**
   * 用oauth2获取用户信息
   *
   * @param code oauth授权返回的代码
   * @return DtOauth2UserInfo
   * @throws DtErrorException 异常
   */
  DtOauth2UserInfo getUserInfo(String code) throws DtErrorException;

}
