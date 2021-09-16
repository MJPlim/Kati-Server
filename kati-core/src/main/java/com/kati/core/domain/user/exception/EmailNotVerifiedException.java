package com.kati.core.domain.user.exception;

public class EmailNotVerifiedException extends IllegalArgumentException {

    public EmailNotVerifiedException(UserExceptionMessage m) {
        super(m.getMessage());
    }

}
