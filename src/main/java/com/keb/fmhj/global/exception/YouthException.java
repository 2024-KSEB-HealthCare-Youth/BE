package com.keb.fmhj.global.exception;

import lombok.Getter;

@Getter
public class YouthException extends RuntimeException {

    private final ErrorCode errorCode;

    private YouthException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public static YouthException from(ErrorCode errorCode) {
        return new YouthException(errorCode);
    }
}
