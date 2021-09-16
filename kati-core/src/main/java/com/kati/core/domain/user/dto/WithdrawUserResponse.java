package com.kati.core.domain.user.dto;

import com.kati.core.domain.user.domain.UserStateType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WithdrawUserResponse {

    private String email;

    private UserStateType state;

}
