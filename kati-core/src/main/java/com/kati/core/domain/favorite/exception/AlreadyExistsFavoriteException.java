package com.kati.core.domain.favorite.exception;

public class AlreadyExistsFavoriteException extends IllegalArgumentException {
    public AlreadyExistsFavoriteException() {
        super(FavoriteExceptionMessage.ALREADY_EXISTS_FAVORITE_EXCEPTION_MESSAGE.getMessage());
    }
}
