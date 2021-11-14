package com.kati.core.domain.user.dto;

import lombok.Getter;

@Getter
public class Oauth2LoginRequest {

    private String loginType;

    private String accessToken;
}
