package com.kati.core.domain.user.dto;

import com.kati.core.domain.user.domain.UserProvider;
import com.kati.core.domain.user.domain.UserRoleType;
import com.kati.core.domain.user.domain.UserStateType;
import com.kati.core.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpUserRequest {

    @Email(message = "이메일 형식에 맞춰 입력해주세요.")
    private String email;

    @NotBlank(message = "패스워드를 입력해주세요.")
    @Size(min = 7, max = 20, message = "패스워드는 7글자 이상 20글자 이하여야 합니다.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 10, message = "이름은 최소 2글자 이상 10글자 이하여야 합니다.")
    private String name;

    private LocalDate birth;

    private String address;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(this.email)
                .password(passwordEncoder.encode(this.password))
                .name(this.name)
                .birth(this.birth)
                .address(this.address)
                .role(UserRoleType.ROLE_USER)
                .state(UserStateType.WAIT)
                .provider(UserProvider.KATI)
                .build();
    }

}
