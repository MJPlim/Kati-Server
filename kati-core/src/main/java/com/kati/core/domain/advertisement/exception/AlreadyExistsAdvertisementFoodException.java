package com.kati.core.domain.advertisement.exception;

public class AlreadyExistsAdvertisementFoodException extends IllegalArgumentException {

    public AlreadyExistsAdvertisementFoodException() {
        super(AdvertisementExceptionMessage.ALREADY_EXISTS_ADVERTISEMENT_FOOD_EXCEPTION_MESSAGE.getMessage());
    }

}
