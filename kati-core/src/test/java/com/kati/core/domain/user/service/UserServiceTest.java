package com.kati.core.domain.user.service;

import com.kati.core.domain.user.domain.User;
import com.kati.core.domain.user.domain.UserProvider;
import com.kati.core.domain.user.dto.*;
import com.kati.core.domain.user.repository.UserRepository;
import com.kati.core.global.config.security.auth.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

@Transactional
@SpringBootTest
@Slf4j
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Mock
    private PrincipalDetails principalDetails;

    Map<String, Object> map;

    @BeforeEach
    void init(){
        Mockito.when(principalDetails.getUsername()).thenReturn("cksgh3422@nate.com");

        map = new HashMap<>();
        Map<String, Object> kakao_account = new HashMap<>();
        kakao_account.put("email", "cksgh3422@navernaver.com");

        Map<String, Object> profile = new HashMap<>();
        profile.put("nickname", "찬호찬호");

        kakao_account.put("profile", profile);
        map.put("kakao_account", kakao_account);

     }

    @Test
    @DisplayName("가입한 유저 정보 저장")
    void saveUser(){
        User user = userService.saveUser(new SignUpUserRequest("cksgh3422@test.com", "12341234", "태스트", LocalDate.now(), "인천광역시"));
        Optional<User> findUser = userRepository.findById(user.getId());

        assertEquals(user.getName(), findUser.get().getName());
        assertEquals(user.getEmail(), findUser.get().getEmail());
        assertEquals(user.getPassword(), findUser.get().getPassword());
    }

    @Test
    @DisplayName("유저가 회원탈퇴 시 유저 상태 변경")
    void withdraw(){
        User withdraw = userService.withdraw(principalDetails, "1234568");
        Optional<User> findUser = userRepository.findById(withdraw.getId());

        log.info("userstate1 = {}", withdraw.getState());
        log.info("userstate2 = {}", findUser.get().getState());
        assertEquals(withdraw.getState(), findUser.get().getState());
    }

    @Test
    @DisplayName("유저의 세컨드 이메일을 설정한다.")
    void setSecondEmail(){
        User user = userService.setSecondEmail(principalDetails, new SetSecondEmailRequest("cksgh3422@test.com"));
        Optional<User> findUser = userRepository.findById(user.getId());

        log.info("usertSecondEmail1 = {}", user.getSecondEmail());
        log.info("usertSecondEmail1 = {}", findUser.get().getSecondEmail());
        assertEquals(user.getSecondEmail(), findUser.get().getSecondEmail());
    }

    @Test
    @DisplayName("세컨드 이메일로 유저가 가입한 이메일을 찾는다.")
    void findEmail(){
        User user = userService.findEmail(new FindEmailRequest("cksgh3422@gmail.com"));
    }

    @Test
    @DisplayName("비밀번호를 찾는다.")
    void findPassword(){
        Optional<User> originUser = userRepository.findByEmail("cksgh3422@nate.com");
        log.info("originUserPassword = {}", originUser.get().getPassword());

        User user = userService.findPassword(new FindPasswordRequest("cksgh3422@nate.com"));
        Optional<User> findUser = userRepository.findById(user.getId());

        log.info("userpassword1 = {}", user.getPassword());
        log.info("userpassword2 = {}", findUser.get().getPassword());
        assertEquals(user.getPassword(), findUser.get().getPassword());

    }


    @Test
    @DisplayName("비밀번호 변경")
    void modifyPassword(){
        User user = userService.modifyPassword(principalDetails, new ModifyPasswordRequest("1234568", "1234567"));
        Optional<User> findUser = userRepository.findById(user.getId());

        log.info("{}",passwordEncoder.matches("1234567", user.getPassword()));
        log.info("userpassword3 = {}", user.getPassword());
        log.info("userpassword4 = {}", findUser.get().getPassword());
        assertEquals(user.getPassword(), findUser.get().getPassword());
    }

    @Test
    @DisplayName("유저정보 반환")
    void getUserInfo(){
        ResponseEntity<UserInfoResponse> userInfo = userService.getUserInfo(principalDetails);
        Optional<User> user = userRepository.findByEmail(principalDetails.getUsername());

        log.info("userinfo1 = {}", userInfo.getBody().getName());
        log.info("userinfo2 = {}", user.get().getName());
        assertEquals(userInfo.getBody().getName(), user.get().getName());
    }


    @Test
    @DisplayName("유저정보 변경")
    void modifyUserInfo(){
        Optional<User> user1 = userRepository.findByEmail(principalDetails.getUsername());
        log.info("userinfo0 = {}", user1.get().getName());

        ResponseEntity<UserInfoResponse> userInfo = userService.modifyUserInfo(principalDetails, new UserInfoModifyRequest("찬메", LocalDate.now(), "인천"));
        Optional<User> user = userRepository.findByEmail(principalDetails.getUsername());

        log.info("userinfo1 = {}", userInfo.getBody().getName());
        log.info("userinfo2 = {}", user.get().getName());
        assertEquals(userInfo.getBody().getName(), user.get().getName());

    }

    @Test
    @DisplayName("로그인한 유저의 리뷰와 좋아요 정보를 보여준다.")
    void userSummary(){
        ResponseEntity<UserSummaryResponse> userSummary = userService.userSummary(principalDetails);
        Optional<User> user = userRepository.findByEmail(principalDetails.getUsername());

        log.info("userinfo1 = {}", userSummary.getBody().getUserName());
        log.info("favoriteCount = {}", userSummary.getBody().getFavoriteCount());
        log.info("reviewCount = {}", userSummary.getBody().getReviewCount());
        log.info("userinfo2 = {}", user.get().getName());
        assertEquals(userSummary.getBody().getUserName(), user.get().getName());
    }

    @Test
    @DisplayName("로그인한 유저정보 반환")
    void getUserByPrincipalDetails(){
        User userPr = userService.getUserByPrincipalDetails(principalDetails);
        Optional<User> user = userRepository.findByEmail(principalDetails.getUsername());

        log.info("userinfo1 = {}", userPr.getName());
        log.info("userinfo2 = {}", user.get().getName());
        assertEquals(userPr.getName(), user.get().getName());
    }

    @Test
    @DisplayName("세컨드 이메일 출력")
    void getSecondEmail(){
        ResponseEntity<GetSecondEmailResponse> secondEmail = userService.getSecondEmail(principalDetails);
        Optional<User> user = userRepository.findByEmail(principalDetails.getUsername());

        log.info("secondEmail = {}", secondEmail.getBody().getSecondEmail());
        log.info("userSecondEmail = {}", user.get().getSecondEmail());
        assertEquals(secondEmail.getBody().getSecondEmail(), user.get().getSecondEmail());
    }

    @Test
    @DisplayName("oauth2로그인 정보를 확인한다.")
    void mappingUser(){
        OAuth2User oAuth2User = userService.mappingUser(map, UserProvider.KAKAO);
        Optional<User> user = userRepository.findByEmail("cksgh3422@navernaver.com");

        Map<String, Object> kakao_account = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakao_account.get("profile");

        log.info("oauth2Name = {}", profile.get("nickname"));
        log.info("userName = {}", user.get().getName());
        assertEquals(profile.get("nickname"), user.get().getName());
    }
}
