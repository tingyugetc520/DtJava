package com.github.tingyugetc520.ali.dingtalk.util.http;

import com.github.tingyugetc520.ali.dingtalk.error.DtError;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

/**
 * 简单的GET请求执行器.
 * 请求的参数是String, 返回的结果也是String
 */
public class OkHttpSimpleGetRequestExecutor implements RequestExecutor<String, String> {
    protected OkHttpClient httpClient;

    public OkHttpSimpleGetRequestExecutor(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public String execute(String uri, String queryParam) throws DtErrorException, IOException {
        if (queryParam != null) {
            if (uri.indexOf('?') == -1) {
                uri += '?';
            }
            uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
        }

        Request request = new Request.Builder().url(uri).build();
        Response response = httpClient.newCall(request).execute();
        return handleResponse(Objects.requireNonNull(response.body()).string());
    }

    protected String handleResponse(String responseContent) throws DtErrorException {
        DtError error = DtError.fromJson(responseContent);
        if (error.getErrorCode() != 0) {
            throw new DtErrorException(error);
        }

        return responseContent;
    }

    public static RequestExecutor<String, String> create(OkHttpClient httpClient) {
        return new OkHttpSimpleGetRequestExecutor(httpClient);
    }
}
