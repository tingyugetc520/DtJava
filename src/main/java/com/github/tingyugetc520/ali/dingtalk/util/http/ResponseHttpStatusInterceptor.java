package com.github.tingyugetc520.ali.dingtalk.util.http;

import com.github.tingyugetc520.ali.dingtalk.error.DtRuntimeErrorEnum;
import com.github.tingyugetc520.ali.dingtalk.error.DtRuntimeException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Slf4j
public class ResponseHttpStatusInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();

        log.debug("");
        Response response = chain.proceed(request);
        if (response.code() != HttpStatus.SC_OK) {
            throw new DtRuntimeException(DtRuntimeErrorEnum.DT_HTTP_CALL_FAILED);
        }

        return response;
    }
}
