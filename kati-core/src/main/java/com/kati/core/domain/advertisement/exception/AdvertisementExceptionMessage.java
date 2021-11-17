package com.kati.core.domain.advertisement.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdvertisementExceptionMessage {

    NOT_FOUND_ADVERTISEMENT_FOOD_EXCEPTION_MESSAGE("해당하는 광고 제품이 없습니다."),
    ALREADY_EXISTS_ADVERTISEMENT_FOOD_EXCEPTION_MESSAGE("이미 광고 제품으로 등록되어 있습니다."),
    SIZE_MORE_THAN_LIST_SIZE_EXCEPTION("랜덤 인덱스 개수가 리스트의 크기보다 큽니다.");

    private final String message;

}
