package com.kati.core.global.domain.mail.service;

import com.kati.core.domain.user.exception.EmailNotFoundException;
import com.kati.core.domain.user.exception.UserExceptionMessage;
import com.kati.core.domain.user.repository.UserRepository;
import com.kati.core.domain.user.domain.UserStateType;
import com.kati.core.domain.user.domain.User;
import com.kati.core.global.domain.mail.domain.EmailAuthCode;
import com.kati.core.global.domain.mail.repository.EmailAuthCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class EmailAuthService {

    private final EmailAuthCodeRepository emailAuthCodeRepository;
    private final UserRepository userRepository;

    @Transactional
    public void emailValidate(String email, String authCode) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(UserExceptionMessage.EMAIL_NOT_FOUND_EXCEPTION_MESSAGE));
        if (user.getState().equals(UserStateType.NORMAL)) return;
        EmailAuthCode emailAuthCode = emailAuthCodeRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(UserExceptionMessage.EMAIL_NOT_FOUND_EXCEPTION_MESSAGE));

        emailAuthCode.validateCode(authCode);
        user.emailVerificationCompleted();
        emailAuthCodeRepository.delete(emailAuthCode);
    }

}
