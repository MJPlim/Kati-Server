package com.kati.core.domain.advertisement.exception;

public class SizeMoreThanListSizeException extends IllegalArgumentException {
    public SizeMoreThanListSizeException() {
        super(AdvertisementExceptionMessage.SIZE_MORE_THAN_LIST_SIZE_EXCEPTION.getMessage());
    }
}
