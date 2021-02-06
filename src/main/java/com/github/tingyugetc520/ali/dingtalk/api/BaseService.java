package com.github.tingyugetc520.ali.dingtalk.api;

import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;
import com.google.gson.JsonObject;

public interface BaseService {
    /**
     * 当本Service没有实现某个API的时候，可以用这个，针对所有钉钉API中的GET请求.
     *
     * @param queryParam 参数
     * @param url        请求接口地址
     * @return 接口响应字符串
     * @throws DtErrorException 异常
     */
    String get(String url, String queryParam) throws DtErrorException;

    /**
     * 当本Service没有实现某个API的时候，可以用这个，针对所有钉钉API中的POST请求.
     *
     * @param postData 请求参数json值
     * @param url      请求接口地址
     * @return 接口响应字符串
     * @throws DtErrorException 异常
     */
    String post(String url, String postData) throws DtErrorException;

    /**
     * 当本Service没有实现某个API的时候，可以用这个，针对所有钉钉API中的POST请求.
     *
     * @param url 请求接口地址
     * @param obj 请求对象
     * @return 接口响应字符串
     * @throws DtErrorException 异常
     */
    String post(String url, Object obj) throws DtErrorException;

    /**
     * 当本Service没有实现某个API的时候，可以用这个，针对所有钉钉API中的POST请求.
     *
     * @param url        请求接口地址
     * @param jsonObject 请求对象
     * @return 接口响应字符串
     * @throws DtErrorException 异常
     */
    String post(String url, JsonObject jsonObject) throws DtErrorException;

}
