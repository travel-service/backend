package com.trablock.web.controller.exception;

/**
 * Member 예외 처리 구조체
 */
public class MemberException extends RuntimeException {

    public MemberException() {
        super();
    }
    public MemberException(String message) {
        super(message);
    }
    public MemberException(String message, Throwable cause) {
        super(message, cause);
    }
    public MemberException(Throwable cause) {
        super(cause);
    }
    protected MemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

