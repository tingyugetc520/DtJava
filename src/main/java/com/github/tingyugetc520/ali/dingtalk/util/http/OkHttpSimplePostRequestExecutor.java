package com.github.tingyugetc520.ali.dingtalk.util.http;

import com.github.tingyugetc520.ali.dingtalk.error.DtError;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

/**
 * 简单的POST请求执行器，请求的参数是String, 返回的结果也是String
 */
public class OkHttpSimplePostRequestExecutor implements RequestExecutor<String, String> {
    protected OkHttpClient httpClient;

    public OkHttpSimplePostRequestExecutor(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public String execute(String uri, String postEntity) throws DtErrorException, IOException {
        RequestBody body = RequestBody.Companion.create(postEntity, MediaType.parse("text/plain; charset=utf-8"));
        Request request = new Request.Builder().url(uri).post(body).build();
        Response response = httpClient.newCall(request).execute();
        return this.handleResponse(Objects.requireNonNull(response.body()).string());
    }

    public String handleResponse(String responseContent) throws DtErrorException {
        if (responseContent.isEmpty()) {
            throw new DtErrorException("无响应内容");
        }

        if (responseContent.startsWith("<xml>")) {
            //xml格式输出直接返回
            return responseContent;
        }

        DtError error = DtError.fromJson(responseContent);
        if (error.getErrorCode() != 0) {
            throw new DtErrorException(error);
        }
        return responseContent;
    }

    public static RequestExecutor<String, String> create(OkHttpClient httpClient) {
        return new OkHttpSimplePostRequestExecutor(httpClient);
    }
}
