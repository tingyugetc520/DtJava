package com.github.tingyugetc520.ali.dingtalk.message;

import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;

public interface DtErrorExceptionHandler {

    void handle(DtErrorException e);

}
