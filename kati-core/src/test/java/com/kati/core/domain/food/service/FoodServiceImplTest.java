package com.kati.core.domain.food.service;

import com.kati.core.domain.food.domain.Food;
import com.kati.core.domain.food.dto.FoodDetailResponse;
import com.kati.core.domain.food.dto.FoodResponse;
import com.kati.core.domain.food.repository.FoodRepository;
import com.kati.core.global.dto.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;


@Transactional
@Slf4j
@SpringBootTest
public class FoodServiceImplTest {

    @Autowired
    FoodServiceImpl foodService;

    @Autowired
    FoodRepository foodRepository;

    @Test
    void findById(){
        Food food = foodService.findById(10101L);
        Optional<Food> byId = foodRepository.findById(10101L);
        log.info("food : {}", byId.get().getFoodName());

        assertEquals(food.getFoodName(), byId.get().getFoodName());
    }

    @Test
    void getFoodDetail(){
        FoodDetailResponse foodDetail = foodService.getFoodDetail(10101L);
        Food food = foodRepository.findById(10101L).get();
        log.info("food : {}", food.getFoodName());

        assertEquals(foodDetail.getFoodName(), food.getFoodName());
        assertEquals(foodDetail.getViewCount(), food.getViewCount());
    }

    @Test
    void findFoodByBarcode(){
        FoodDetailResponse foodByBarcode = foodService.findFoodByBarcode("8801074000130");
        Food food = foodRepository.findByBarcodeNumber("8801074000130").get();
        log.info("food : {}", food.getFoodName());

        assertEquals(foodByBarcode.getFoodName(), food.getFoodName());
        assertEquals(foodByBarcode.getViewCount(), food.getViewCount());

    }

    @Test
    void searchFood(){
        Pagination<List<FoodResponse>> listPagination = foodService.searchFood(1, 10, null, "asc", null, "설화눈꽃팝김부각스낵", null, null);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Food> search = foodRepository.search(null, null, "설화눈꽃팝김부각스낵", null, null, "asc", pageable);
        log.info("food : {}", search.stream().findFirst().get().getFoodName());
        log.info("food : {}", search.stream().findFirst().get().getViewCount());

        assertEquals(listPagination.getData().stream().findFirst().get().getFoodName(), search.stream().findFirst().get().getFoodName());
        assertEquals(listPagination.getData().stream().findFirst().get().getManufacturerName(), search.stream().findFirst().get().getManufacturerName());
    }

//    @Test
//    void findFoodByWideCategory(){
//            //???
//    }
}
