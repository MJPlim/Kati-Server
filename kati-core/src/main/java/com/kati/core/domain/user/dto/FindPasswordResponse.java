package com.kati.core.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindPasswordResponse {

    private final String email;

}
