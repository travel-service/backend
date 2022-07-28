package com.trablock.web.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class MemberHasNoneOwnershipException extends Exception {

    public MemberHasNoneOwnershipException(String message) {
        super(message);
    }
}
