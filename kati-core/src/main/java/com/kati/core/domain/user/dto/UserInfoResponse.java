package com.kati.core.domain.user.dto;

import com.kati.core.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfoResponse {

    private String name;
    private LocalDate birth;
    private String address;

    public static UserInfoResponse from(User user) {
        return new UserInfoResponse(user.getName(), user.getBirth(), user.getAddress());
    }

}
