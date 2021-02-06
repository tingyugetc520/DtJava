package com.github.tingyugetc520.ali.dingtalk.util.http;

/**
 * http请求响应回调处理接口.
 */
public interface ResponseHandler<T> {
  /**
   * 响应结果处理.
   *
   * @param t 要处理的对象
   */
  void handle(T t);
}
