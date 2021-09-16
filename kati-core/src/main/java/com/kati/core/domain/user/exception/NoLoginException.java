package com.kati.core.domain.user.exception;

public class NoLoginException extends IllegalArgumentException {

    public NoLoginException(UserExceptionMessage m) {
        super(m.getMessage());
    }

}
