package com.kati.core.domain.user.dto;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindPasswordRequest {

    @Email(message = "이메일 형식에 맞춰 입력해주세요.")
    private String email;

}
