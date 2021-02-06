package com.github.tingyugetc520.ali.dingtalk.util.http;

import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;

import java.io.IOException;

/**
 * http请求执行器.
 *
 * @param <T> 返回值类型
 * @param <E> 请求参数类型
 */
public interface RequestExecutor<T, E> {

  /**
   * 执行http请求.
   *
   * @param uri    uri
   * @param data   数据
   * @return 响应结果
   * @throws DtErrorException 自定义异常
   * @throws IOException      io异常
   */
  T execute(String uri, E data) throws DtErrorException, IOException;

}
