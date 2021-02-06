package com.github.tingyugetc520.ali.dingtalk.error;

public class DtErrorException extends Exception {
    private static final long serialVersionUID = -6357149550353160810L;

    private final DtError error;

    public DtErrorException(String message) {
        this(DtError.builder().errorCode(-1).errorMsg(message).build());
    }

    public DtErrorException(DtError error) {
        super(error.toString());
        this.error = error;
    }

    public DtErrorException(DtError error, Throwable cause) {
        super(error.toString(), cause);
        this.error = error;
    }

    public DtErrorException(Throwable cause) {
        super(cause.getMessage(), cause);
        this.error = DtError.builder().errorCode(-1).errorMsg(cause.getMessage()).build();
    }

    public DtError getError() {
        return this.error;
    }

}
