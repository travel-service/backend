package com.trablock.web.exception;

public class ToManyUserDirectoryException extends RuntimeException{

    public ToManyUserDirectoryException() {
        super();
    }

    public ToManyUserDirectoryException(String message) {
        super(message);
    }

    public ToManyUserDirectoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public ToManyUserDirectoryException(Throwable cause) {
        super(cause);
    }

    protected ToManyUserDirectoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
