package com.kati.core.domain.user.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kati.core.domain.user.domain.User;
import com.kati.core.domain.user.domain.UserProvider;
import com.kati.core.domain.user.dto.*;
import com.kati.core.domain.user.exception.NoLoginException;
import com.kati.core.domain.user.exception.UserExceptionMessage;
import com.kati.core.domain.user.service.UserService;
import com.kati.core.global.config.security.auth.PrincipalDetails;
import com.kati.core.global.config.security.jwt.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Api(tags = {"User"})
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final RestTemplate restTemplate;
    private final JwtTokenProvider jwtTokenProvider;


    @ApiOperation(value = "회원가입", notes = "사용자가 회원가입을 한다")
    @PostMapping("signup")
    public ResponseEntity<SignUpUserResponse> signup(@Valid @RequestBody SignUpUserRequest dto) {
        User saved = userService.saveUser(dto);
        return ResponseEntity.ok(SignUpUserResponse.builder()
                .email(saved.getEmail())
                .message("해당 메일 주소로 이메일 인증 메일을 발송했습니다. 메일 인증을 하시면 회원가입이 완료됩니다.")
                .build());
    }

    @ApiOperation(value = "회원탈퇴", notes = "회원을 탈퇴시킨다")
    @PostMapping("api/v1/user/withdraw")
    public ResponseEntity<WithdrawUserResponse> withdraw(@AuthenticationPrincipal PrincipalDetails principal, @RequestBody WithdrawUserRequest dto) {
        User withdrew = userService.withdraw(principal, dto.getPassword());
        return ResponseEntity.ok(WithdrawUserResponse.builder()
                .email(withdrew.getEmail())
                .state(withdrew.getState())
                .build());
    }

    @ApiOperation(value = "2차보안 설정하기", notes = "2차보안용 이메일을 설정한다.")
    @PostMapping("api/v1/user/set-secondEmail")
    public ResponseEntity<SetSecondEmailResponse> setSecondEmail(@AuthenticationPrincipal PrincipalDetails principal
            , @Valid @RequestBody SetSecondEmailRequest request) {
        User user = this.userService.setSecondEmail(principal, request);
        return ResponseEntity.ok(SetSecondEmailResponse.builder()
                .secondEmail(user.getSecondEmail())
                .build());
    }

    @ApiOperation(value = "복구 이메일 반환", notes = "토큰을 받아 해당하는 복구 이메일을 반환해준다")
    @GetMapping("api/v1/user/get-secondEmail")
    public ResponseEntity<GetSecondEmailResponse> getSecondEmail(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return this.userService.getSecondEmail(principalDetails);
    }

    @ApiOperation(value = "아이디 찾기", notes = "2차보안으로 설정한 이메일로 기존 이메일을 전송한다")
    @PostMapping("find-email")
    public ResponseEntity<FindEmailResponse> findEmail(@Valid @RequestBody FindEmailRequest request) {
        User user = this.userService.findEmail(request);
        return ResponseEntity.ok(FindEmailResponse.builder()
                .secondEmail(user.getSecondEmail())
                .build());
    }

    @ApiOperation(value = "패스워드 찾기", notes = "해당 메일 주소로 임시 패스워드를 전송한다.")
    @PostMapping("find-password")
    public ResponseEntity<FindPasswordResponse> findPassword(@Valid @RequestBody FindPasswordRequest dto) {
        User user = userService.findPassword(dto);
        return ResponseEntity.ok(FindPasswordResponse.builder()
                .email(user.getEmail())
                .build());
    }

    @GetMapping("oauth-success")
    public ResponseEntity<String> user(HttpServletResponse response, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails == null) {
            throw new IllegalArgumentException("로그인을 해주세요.");
        } else if (!principalDetails.isEnabled()) {
            throw new IllegalArgumentException("회원탈퇴된 계정입니다.");
        }
        return ResponseEntity.ok("로그인 성공");
    }

    @ApiOperation(value = "패스워드 변경", notes = "패스워드 변경을 요청한다.")
    @PostMapping("api/v1/user/modify-password")
    public ResponseEntity<ModifyPasswordResponse> modifyPassword(@AuthenticationPrincipal PrincipalDetails principal,
                                                                 @Valid @RequestBody ModifyPasswordRequest dto) {
        if (principal == null) throw new NoLoginException(UserExceptionMessage.NO_LOGIN_EXCEPTION_MESSAGE);
        User user = userService.modifyPassword(principal, dto);
        return ResponseEntity.ok(ModifyPasswordResponse.builder()
                .message("패스워드 변경 완료")
                .build());
    }

    @GetMapping("api/v1/user/user-info")
    public ResponseEntity<UserInfoResponse> getUserInfo(@AuthenticationPrincipal PrincipalDetails principal) {
        return userService.getUserInfo(principal);
    }

    @PostMapping("api/v1/user/modify-user-info")
    public ResponseEntity<UserInfoResponse> modifyUserInfo(@AuthenticationPrincipal PrincipalDetails principal,
                                                           @Valid @RequestBody UserInfoModifyRequest request) {
        return userService.modifyUserInfo(principal, request);
    }

    @GetMapping("api/v1/user/summary")
    public ResponseEntity<UserSummaryResponse> userSummary(@AuthenticationPrincipal PrincipalDetails principal) {
        return userService.userSummary(principal);
    }

    @CrossOrigin(origins = {"http://3.38.97.234:8000/core-service/", "http://localhost:3000/"})
    @PostMapping(value = "/oauth2-login")
    public ResponseEntity<Oauth2LoginResponse> oauth2Login(@RequestBody Oauth2LoginRequest oauth2) throws ServletException, IOException {

        String url = "";
        UserProvider provider = null;
        if(oauth2.getLoginType().equals("KAKAO")) {
            url = "https://kapi.kakao.com/v2/user/me";
            provider = UserProvider.KAKAO;
        }else if(oauth2.getLoginType().equals("NAVER")){
            url = "https://openapi.naver.com/v1/nid/me";
            provider = UserProvider.NAVER;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + oauth2.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity header = new HttpEntity(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, header, String.class);
        System.out.println(responseEntity.getBody().toString());

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        Map<String, Object> map = gson.fromJson(responseEntity.getBody().toString(), Map.class);
        PrincipalDetails oAuth2User = (PrincipalDetails) userService.mappingUser(map, provider);

        if(oAuth2User != null){
            HttpHeaders responseHeaders = new HttpHeaders();
            String token = jwtTokenProvider.createToken(oAuth2User.getUsername());
            responseHeaders.set("Authorization", "Bearer " + token);
            return ResponseEntity.ok().headers(responseHeaders).body(Oauth2LoginResponse.builder().message("로그인 성공!").build());
        }else{
            return ResponseEntity.badRequest().body(Oauth2LoginResponse.builder().message("로그인 실패!").build());
        }
        
    }

}
