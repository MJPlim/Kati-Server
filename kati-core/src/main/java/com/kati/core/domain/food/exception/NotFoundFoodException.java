package com.kati.core.domain.food.exception;

import java.util.NoSuchElementException;

public class NotFoundFoodException extends NoSuchElementException {

    public NotFoundFoodException() {
        super(FoodExceptionMessage.NOT_FOUND_FOOD_EXCEPTION_MESSAGE.getMessage());
    }

    public NotFoundFoodException(FoodExceptionMessage m) {
        super(m.getMessage());
    }
}
