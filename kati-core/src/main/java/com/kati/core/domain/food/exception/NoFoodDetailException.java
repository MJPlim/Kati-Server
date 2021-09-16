package com.kati.core.domain.food.exception;

import java.util.NoSuchElementException;

public class NoFoodDetailException extends NoSuchElementException {
    public NoFoodDetailException(FoodExceptionMessage m) {
        super(m.getMessage());
    }
}
