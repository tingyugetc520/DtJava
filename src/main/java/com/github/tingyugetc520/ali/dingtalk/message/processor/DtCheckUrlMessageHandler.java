package com.github.tingyugetc520.ali.dingtalk.message.processor;

import com.github.tingyugetc520.ali.dingtalk.api.DtService;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtEventMessage;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;
import com.github.tingyugetc520.ali.dingtalk.message.DtMessageHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class DtCheckUrlMessageHandler implements DtMessageHandler {

    @Override
    public Boolean handle(DtEventMessage message, Map<String, Object> context, DtService dtService) throws DtErrorException {
        // check url事件直接返回true，因为已经解析成功了
        return true;
    }

}
