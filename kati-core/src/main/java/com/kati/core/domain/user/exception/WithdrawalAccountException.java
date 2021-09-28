package com.kati.core.domain.user.exception;

public class WithdrawalAccountException extends IllegalArgumentException {

    public WithdrawalAccountException() {
        super(UserExceptionMessage.WITHDRAWAL_ACCOUNT_EXCEPTION_MESSAGE.getMessage());
    }

    public WithdrawalAccountException(UserExceptionMessage e) {
        super(e.getMessage());
    }

}
