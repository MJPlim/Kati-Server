package com.kati.core.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoModifyRequest {

    @NotBlank(message = "이름을 확인해주세요.")
    private String name;

    private LocalDate birth;

    @NotBlank(message = "주소를 확인해주세요.")
    private String address;

}
