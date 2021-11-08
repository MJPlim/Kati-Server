package com.kati.core.domain.favorite.service;

import com.kati.core.domain.favorite.dto.FavoriteResponse;
import com.kati.core.domain.favorite.exception.AlreadyExistsFavoriteException;
import com.kati.core.global.config.security.auth.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class FavoriteServiceTest {

    @Mock
    PrincipalDetails principalDetails;

    @Autowired
    FavoriteService favoriteService;

    @BeforeEach
    void init() {
        Mockito.when(principalDetails.getUsername()).thenReturn("likeharrybro@gmail.com");
        LongStream.rangeClosed(1, 10).forEach(i -> this.favoriteService.add(this.principalDetails, i));
    }

    @Test
    @DisplayName("유저의 즐겨찾기 음식 리스트를 가져온다")
    void getFavoriteFoodListTest() {
        List<FavoriteResponse> list = this.favoriteService.findAll(this.principalDetails);
        this.logFavoriteList(list);
        assertEquals(10, list.size());
    }

    @Test
    @DisplayName("유저가 즐겨찾기 음식을 추가한다")
    void addFavoriteFood() {
        this.favoriteService.add(this.principalDetails, 11L);
        List<FavoriteResponse> list = this.favoriteService.findAll(this.principalDetails);
        this.logFavoriteList(list);
        assertEquals(11, list.size());
    }

    @Test
    @DisplayName("이미 추가된 즐겨찾기 음식이라면 즐겨찾기에 추가에 실패한다")
    void failToAlreadyExistsFood() {
        assertThrows(AlreadyExistsFavoriteException.class, () -> this.favoriteService.add(this.principalDetails, 1L));
        this.logFavoriteList(this.favoriteService.findAll(this.principalDetails));
    }

    @Test
    @DisplayName("유저의 즐겨찾기 음식을 삭제한다")
    void testName() {
        this.favoriteService.delete(this.principalDetails, 1L);
        List<FavoriteResponse> list = this.favoriteService.findAll(this.principalDetails);
        this.logFavoriteList(list);
        assertEquals(9, list.size());
    }

    private void logFavoriteList(List<FavoriteResponse> list) {
        list.forEach(favorite -> log.info(String.format("%s %s", favorite.getFood().getFoodId(), favorite.getFood().getFoodName())));
    }

}