package com.github.tingyugetc520.ali.dingtalk.message.processor;

import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;
import com.github.tingyugetc520.ali.dingtalk.message.DtErrorExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DtLogExceptionHandler implements DtErrorExceptionHandler {
    @Override
    public void handle(DtErrorException e) {
        log.error("Error happens", e);
    }

}
