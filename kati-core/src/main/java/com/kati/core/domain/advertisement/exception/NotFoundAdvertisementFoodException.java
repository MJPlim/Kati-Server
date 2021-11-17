package com.kati.core.domain.advertisement.exception;

import java.util.NoSuchElementException;

public class NotFoundAdvertisementFoodException extends NoSuchElementException {

    public NotFoundAdvertisementFoodException() {
        super(AdvertisementExceptionMessage.NOT_FOUND_ADVERTISEMENT_FOOD_EXCEPTION_MESSAGE.getMessage());
    }

    public NotFoundAdvertisementFoodException(String message) {
        super(message);
    }
}
