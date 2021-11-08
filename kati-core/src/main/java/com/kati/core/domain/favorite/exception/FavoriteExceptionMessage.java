package com.kati.core.domain.favorite.exception;

public enum FavoriteExceptionMessage {
    ALREADY_EXISTS_FAVORITE_EXCEPTION_MESSAGE("이미 즐겨찾기에 추가된 상품입니다.");

    private final String message;

    FavoriteExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
