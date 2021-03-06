package com.kati.core.domain.food.service;

import com.kati.core.domain.food.domain.Food;
import com.kati.core.global.dto.Pagination;
import com.kati.core.domain.food.dto.FoodDetailResponse;
import com.kati.core.domain.food.dto.FoodResponse;

import java.util.List;

public interface FoodService {

    Food findById(Long id);

    FoodDetailResponse getFoodDetail(Long foodId);

    FoodDetailResponse findFoodByBarcode(String barcode);

    Pagination<List<FoodResponse>> searchFood(int pageNo, int size, String sortElement, String order, String category
            , String foodName, String manufacturerName, List<String> allergyList);

    Pagination<List<FoodResponse>> findFoodByWideCategory(String category, int page, String sort, int size);

    //    int makeFoodDatabaseWithoutBarCodeAPI();

}
