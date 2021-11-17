package com.kati.core.domain.user.service;

import com.kati.core.domain.favorite.repository.FavoriteRepository;
import com.kati.core.domain.post.repository.CommentRepository;
import com.kati.core.domain.review.repository.ReviewRepository;
import com.kati.core.domain.user.domain.User;
import com.kati.core.domain.user.domain.UserProvider;
import com.kati.core.domain.user.domain.UserRoleType;
import com.kati.core.domain.user.domain.UserStateType;
import com.kati.core.domain.user.dto.*;
import com.kati.core.domain.user.exception.*;
import com.kati.core.domain.user.repository.UserRepository;
import com.kati.core.global.config.security.auth.PrincipalDetails;
import com.kati.core.global.domain.mail.domain.EmailAuthCode;
import com.kati.core.global.domain.mail.domain.EmailSubject;
import com.kati.core.global.domain.mail.repository.EmailAuthCodeRepository;
import com.kati.core.global.domain.mail.util.EmailAuthCodeGenerator;
import com.kati.core.global.domain.mail.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailAuthCodeRepository emailAuthCodeRepository;
    private final FavoriteRepository favoriteRepository;
    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;
    private final EmailUtil emailUtil;

    public User saveUser(SignUpUserRequest dto) {
        if (emailAuthCodeRepository.existsByEmail(dto.getEmail()))
            throw new EmailNotVerifiedException(UserExceptionMessage.EMAIL_NOT_VERIFIED_EXCEPTION_MESSAGE);
        if (userRepository.existsByEmail(dto.getEmail()))
            throw new EmailDuplicateException(UserExceptionMessage.EMAIL_DUPLICATE_EXCEPTION_MESSAGE);

        EmailAuthCodeGenerator authCodeGenerator = new EmailAuthCodeGenerator();
        String authCode = authCodeGenerator.generateAuthCode();
        emailAuthCodeRepository.save(EmailAuthCode.builder()
                .email(dto.getEmail())
                .authCode(authCode)
                .build());

        String message = emailUtil.getEmailAuthMessage(dto.getEmail(), authCode);
        emailUtil.sendEmail(dto.getEmail(), EmailSubject.EMAIL_AUTH_REQUEST, message);

        return userRepository.save(dto.toEntity(passwordEncoder));
    }

    @Transactional
    public User withdraw(PrincipalDetails principal, String password) {
        User user = getUserByPrincipalDetails(principal);
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if (!matches)
            throw new PasswordMismatchException(UserExceptionMessage.PASSWORD_MISMATCH_EXCEPTION_MESSAGE);
        user.withdraw();
        return user;
    }

    @Transactional
    public User setSecondEmail(PrincipalDetails principal, SetSecondEmailRequest request) {
        if (userRepository.existsBySecondEmail(request.getSecondEmail()))
            throw new SecondEmailDuplicateException(UserExceptionMessage.SECOND_EMAIL_DUPLICATE_EXCEPTION_MESSAGE);

        User user = getUserByPrincipalDetails(principal);
        user.setSecondEmail(request.getSecondEmail());

        return user;
    }

    public User findEmail(FindEmailRequest request) {
        User user = this.userRepository.findBySecondEmail(request.getSecondEmail())
                .orElseThrow(() -> new UsernameNotFoundException(UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));

        String msg = this.emailUtil.getFindEmailMessage(user.getEmail());
        this.emailUtil.sendEmail(request.getSecondEmail(), EmailSubject.FIND_EMAIL_REQUEST, msg);

        return user;
    }

    @Transactional
    public User findPassword(FindPasswordRequest dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));
        this.validateUser(user);
        EmailAuthCodeGenerator authCodeGenerator = new EmailAuthCodeGenerator();
        String authCode = authCodeGenerator.generateAuthCode();
        user.updatePassword(passwordEncoder.encode(authCode));

        String message = emailUtil.getFindPasswordMessage(dto.getEmail(), authCode);
        emailUtil.sendEmail(dto.getEmail(), EmailSubject.FIND_PASSWORD_REQUEST, message);

        return user;
    }

    private void validateUser(User user) {
        if (user.getState().equals(UserStateType.DELETED)) throw new WithdrawalAccountException();
        if (user.getState().equals(UserStateType.WAIT)) throw new EmailNotVerifiedException();
    }

    @Transactional
    public User modifyPassword(PrincipalDetails principal, ModifyPasswordRequest dto) {
        User user = this.getUserByPrincipalDetails(principal);
        //입력한 비밀번호가 기존 비밀번호와 다를때
        boolean matches1 = passwordEncoder.matches(dto.getBeforePassword(), user.getPassword());
        if (!matches1)
            throw new PasswordMismatchException(UserExceptionMessage.PASSWORD_MISMATCH_EXCEPTION_MESSAGE);

        //변경할 비밀번호가 기존 비밀번호와 같을때
        boolean matches2 = passwordEncoder.matches(dto.getAfterPassword(), user.getPassword());
        if (matches2)
            throw new PasswordDuplicatedException(UserExceptionMessage.PASSWORD_DUPLICATED_EXCEPTION_MESSAGE);

        user.updatePassword(passwordEncoder.encode(dto.getAfterPassword()));

        return user;
    }

    public ResponseEntity<UserInfoResponse> getUserInfo(PrincipalDetails principal) {
        return ResponseEntity.ok(UserInfoResponse.from(getUserByPrincipalDetails(principal)));
    }

    @Transactional
    public ResponseEntity<UserInfoResponse> modifyUserInfo(PrincipalDetails principal, UserInfoModifyRequest request) {
        return ResponseEntity.ok(UserInfoResponse.from(getUserByPrincipalDetails(principal).modifyUserInfo(request)));
    }

    public ResponseEntity<UserSummaryResponse> userSummary(PrincipalDetails principal) {
        User user = getUserByPrincipalDetails(principal);
        Long favoriteCount = this.favoriteRepository.countAllByUser(user);
        Long reviewCount = this.reviewRepository.countAllByUser(user);
        return ResponseEntity.ok(UserSummaryResponse.of(user.getName(), favoriteCount, reviewCount));
    }

    public User getUserByPrincipalDetails(PrincipalDetails principal) {
        return userRepository.findByEmail(principal.getUsername()).orElseThrow(NotFoundUserException::new);
    }

    public ResponseEntity<GetSecondEmailResponse> getSecondEmail(PrincipalDetails principalDetails) {
        User user = getUserByPrincipalDetails(principalDetails);
        return ResponseEntity.ok(GetSecondEmailResponse.builder().secondEmail(user.getSecondEmail()).build());
    }

    public OAuth2User mappingUser(Map<String, Object> userRequest, UserProvider provider) {

        UserProvider userProvider = checkProvider(provider.toString());
        String email = null;
        String nickname = null;
        if (userProvider.equals(UserProvider.KAKAO)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) userRequest.get("kakao_account");
            email = kakaoAccount.get("email").toString();
            Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
            nickname = kakaoProfile.get("nickname").toString();
        } else if (userProvider.equals(UserProvider.NAVER)) {
            Map<String, Object> naverPropile = (Map<String, Object>) userRequest.get("response");
            email = (String) naverPropile.get("email");
            nickname = (String) naverPropile.get("nickname");
        }
        final String finalEmail = email;
        final String finalNickname = nickname;

        User user = userRepository.findByEmailAndProviderIs(email, userProvider).orElseGet(() -> {
            if (userRepository.existsByEmail(finalEmail)) {
                throw new AuthenticationServiceException(UserExceptionMessage.EMAIL_DUPLICATE_EXCEPTION_MESSAGE.getMessage());
            }
            System.out.println("created email");
            return userRepository.save(User.builder()
                    .email(finalEmail)
                    .password(passwordEncoder.encode("KATI"))  //??
                    .name(finalNickname)
                    .role(UserRoleType.ROLE_USER)
                    .state(UserStateType.NORMAL)
                    .provider(userProvider)
                    .build());
        });

        return new PrincipalDetails(user, userRequest);
    }

    private UserProvider checkProvider(String provider) {
        if (provider.equals("NAVER")) {
            return UserProvider.NAVER;
        } else if (provider.equals("KAKAO")) {
            return UserProvider.KAKAO;
        }
        return UserProvider.KATI;
    }
}
