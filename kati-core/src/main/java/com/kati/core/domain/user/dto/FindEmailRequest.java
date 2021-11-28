package com.kati.core.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindEmailRequest {

    @Email(message = "이메일 형식에 맞춰 입력해주세요.")
    private String secondEmail;
}
