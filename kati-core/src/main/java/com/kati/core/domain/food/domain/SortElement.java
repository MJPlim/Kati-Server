package com.kati.core.domain.food.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortElement {

    RANK("ranking"),
    MANUFACTURER("manufacturer"),
    REVIEW_COUNT("reviewCount");

    private final String message;
}
