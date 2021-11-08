package com.kati.core.domain.user.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class NotFoundUserException extends UsernameNotFoundException {

    public NotFoundUserException() {
        super(UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage());
    }

}
