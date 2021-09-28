package com.kati.core.domain.user.exception;

public class EmailNotVerifiedException extends IllegalArgumentException {

    public EmailNotVerifiedException() {
        super(UserExceptionMessage.EMAIL_NOT_VERIFIED_EXCEPTION_MESSAGE.getMessage());
    }

    public EmailNotVerifiedException(UserExceptionMessage m) {
        super(m.getMessage());
    }

}
