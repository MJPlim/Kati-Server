package com.kati.core.global.config.security.auth;

import com.kati.core.domain.user.domain.User;
import com.kati.core.domain.user.domain.UserProvider;
import com.kati.core.domain.user.domain.UserRoleType;
import com.kati.core.domain.user.domain.UserStateType;
import com.kati.core.domain.user.exception.EmailDuplicateException;
import com.kati.core.domain.user.exception.UserExceptionMessage;
import com.kati.core.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class PrincipalOauth2DetailsService extends DefaultOAuth2UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getClientName();

        UserProvider userProvider = checkProvider(provider);
        String email = null;
        String nickname = null;
        if(userProvider.equals(UserProvider.KAKAO)){
            Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
            email = (String) kakaoAccount.get("email");
            Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
            nickname = (String) kakaoProfile.get("nickname");
        }else if(userProvider.equals(UserProvider.NAVER)){
            Map<String, Object> naverPropile = oAuth2User.getAttribute("response");
            email = (String) naverPropile.get("email");
            nickname = (String) naverPropile.get("nickname");
        }
        final String finalEmail = email;
        final String finalNickname = nickname;

        User user = userRepository.findByEmailAndProviderIs(email, userProvider).orElseGet(() -> {
            if (userRepository.existsByEmail(finalEmail))
                throw new EmailDuplicateException(UserExceptionMessage.EMAIL_DUPLICATE_EXCEPTION_MESSAGE);
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

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }

    private UserProvider checkProvider(String provider) {
        if (provider.equals("Naver")) {
            return UserProvider.NAVER;
        } else if (provider.equals("Kakao")) {
            return UserProvider.KAKAO;
        }
        return UserProvider.KATI;
    }
}
