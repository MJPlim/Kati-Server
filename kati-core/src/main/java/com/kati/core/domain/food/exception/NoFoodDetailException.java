package com.kati.core.domain.food.exception;

import java.util.NoSuchElementException;

public class NoFoodDetailException extends NoSuchElementException {

    public NoFoodDetailException() {
        super(FoodExceptionMessage.NO_FOOD_DETAIL_EXCEPTION_MESSAGE.getMessage());
    }

    public NoFoodDetailException(FoodExceptionMessage m) {
        super(m.getMessage());
    }
}
