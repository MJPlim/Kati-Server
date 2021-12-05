package com.kati.core.domain.user.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Role Test"})
@RequiredArgsConstructor
@RestController
public class RoleTestController {

    private final Environment env;

    @ApiOperation(value = "유저 권한 테스트", notes = "유저의 권한을 테스트한다")
    @GetMapping("/api/v1/user")
    public String user() {
        return "user";
    }

    @ApiOperation(value = "관리자 권한 테스트", notes = "관리자의 권한을 테스트한다")
    @GetMapping("/api/v1/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/api/health/check")
    public String healthCheck() {
        return this.env.getProperty("message.hello");
    }

}
