package com.github.tingyugetc520.ali.dingtalk.error;

import org.jetbrains.annotations.NotNull;

/**
 * DtJava专用的runtime exception
 */
public class DtRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 4881698471192264412L;

    private long code;

    public DtRuntimeException(Throwable e) {
        super(e);
    }

    public DtRuntimeException(String msg) {
        super(msg);
    }

    public DtRuntimeException(String msg, Throwable e) {
        super(msg, e);
    }

    public DtRuntimeException(@NotNull DtRuntimeErrorEnum errorEnum) {
        super(errorEnum.getMsg());
        this.code = errorEnum.getCode();
    }

    public DtRuntimeException(long code, String msg) {
        super(msg);
        this.code = code;
    }

    public long getCode() {
        return code;
    }
}
