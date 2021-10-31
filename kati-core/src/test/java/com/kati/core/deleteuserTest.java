package com.kati.core;

import com.kati.core.domain.user.domain.User;
import com.kati.core.domain.user.domain.UserProvider;
import com.kati.core.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
public class deleteuserTest {

    @Autowired
    private UserRepository userRepository;

    //삭제 테스트
//    @Test
//    @Rollback(false)
//    void deleteTest(){
//        User user = userRepository.findById(331L).get();
//        System.out.println(user.getName());
//
//        userRepository.delete(user);
//    }


//    @Test
//    void findTest(){
//        User user = userRepository.findByEmailAndProviderIs("cksgh3422@naver.com", UserProvider.KAKAO).get();
//        System.out.println(user.getName());
//        System.out.println(user.getProvider());
//        Assertions.assertEquals(user.getName(), "정찬호");
//        Assertions.assertNull(user.getName());
//    }

}
