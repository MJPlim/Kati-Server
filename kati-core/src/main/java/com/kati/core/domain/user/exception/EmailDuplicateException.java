package com.kati.core.domain.user.exception;

public class EmailDuplicateException extends IllegalArgumentException {

    public EmailDuplicateException(UserExceptionMessage m) {
        super(m.getMessage());
    }

}
