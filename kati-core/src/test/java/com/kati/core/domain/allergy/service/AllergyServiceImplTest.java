package com.kati.core.domain.allergy.service;

import com.kati.core.domain.allergy.domain.UserAllergy;
import com.kati.core.global.config.security.auth.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@Transactional
@SpringBootTest
@Slf4j
public class AllergyServiceImplTest {
    @Autowired
    private AllergyServiceImpl allergyService;

    @Mock
    private PrincipalDetails principalDetails;

    List<String> allergyList;

    @BeforeEach
    void init(){
        Mockito.when(principalDetails.getUsername()).thenReturn("cksgh3422@nate.com");
        allergyList = new ArrayList<String>();
        allergyList.add("새우");
        allergyList.add("우유");
    }

    @Test
    @DisplayName("알러지 저장")
    void saveUserAllergy(){
        allergyService.saveUserAllergy(principalDetails, allergyList);

        List<String> userAllergy = allergyService.findUserAllergy(principalDetails);

        assertEquals(allergyList.get(0),userAllergy.get(0));
        assertEquals(allergyList.get(1),userAllergy.get(1));
    }


    @Test
    @DisplayName("유저 알러지 검색")
    void findUserAllergy(){
        List<String> userAllergy = allergyService.findUserAllergy(principalDetails);
        assertEquals(allergyList.get(0),userAllergy.get(0));
        assertEquals(allergyList.get(1),userAllergy.get(1));
    }
}
