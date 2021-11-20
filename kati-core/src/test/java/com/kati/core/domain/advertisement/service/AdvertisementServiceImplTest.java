package com.kati.core.domain.advertisement.service;

import com.kati.core.domain.advertisement.domain.AdvertisementFood;
import com.kati.core.domain.advertisement.exception.AlreadyExistsAdvertisementFoodException;
import com.kati.core.domain.advertisement.exception.NotFoundAdvertisementFoodException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@Transactional
@SpringBootTest
class AdvertisementServiceImplTest {

    @Autowired
    AdvertisementServiceImpl advertisementService;

    List<Long> advertisementFoodsIds;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        this.advertisementFoodsIds = new ArrayList<>();
        LongStream.rangeClosed(1, 5).forEach(i -> {
            try {
                this.advertisementFoodsIds.add(this.advertisementService.saveByFoodId(i).getId());
            } catch (AlreadyExistsAdvertisementFoodException e) {
                log.info("foodId: {} errorMessage: {}", i, e.getMessage());
            }
        });
    }

    @ParameterizedTest(name = "size: {arguments} 지정한 size 만큼 랜덤한 광고를 가져온다.")
    @ValueSource(ints = {2, 4})
    void getRandomAdvertisementFoods(int size) {
        Set<AdvertisementFood> randomAdvertisementFoods = this.advertisementService.getRandomAdvertisementFoods(size);
        randomAdvertisementFoods.forEach(a ->
                log.info("advertisementId: {} foodId: {} foodName: {}",
                        a.getId(), a.getFood().getId(), a.getFood().getFoodName()));
        assertEquals(size, randomAdvertisementFoods.size());
    }

    @DisplayName("광고 ID로 해당 광고 제품을 가져온다.")
    @Test
    void findById() {
        this.advertisementFoodsIds.forEach(id -> {
            AdvertisementFood advertisementFood = this.advertisementService.findById(id);
            log.info("id: {}", advertisementFood.getId());
            assertEquals(id, advertisementFood.getId());
        });
    }

    @DisplayName("존재하지 않는 광고제품을 가져오려고 할 시 에러가 발생한다.")
    @Test
    void notFoundAdvertisementsFood() {
        assertThrows(NotFoundAdvertisementFoodException.class, () -> this.advertisementService.findById(Long.MAX_VALUE));
    }

    @Test
    @DisplayName("이미 광고로 등록되어 있는 제품은 등록할 수 없다.")
    void testName() {
        assertThrows(AlreadyExistsAdvertisementFoodException.class, () ->
                LongStream.rangeClosed(1, 5).forEach(this.advertisementService::saveByFoodId));
    }

}