package com.kati.core.domain.food.api;

import com.kati.core.domain.food.service.FoodService;
import com.kati.core.global.dto.Pagination;
import com.kati.core.domain.food.dto.FindFoodByBarcodeRequest;
import com.kati.core.domain.food.dto.FoodDetailResponse;
import com.kati.core.domain.food.dto.FoodResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Food"})
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/food")
public class FoodController {
    private final FoodService foodService;

    @ApiOperation(value = "특정 제품의 상세정보 조회", notes = "선택한 제품을 조회하여 상세정보를 반환한다")
    @GetMapping("/findFood/foodDetail")
    public FoodDetailResponse getFoodDetail(@RequestParam(name = "foodId") Long foodId) {
        return this.foodService.getFoodDetail(foodId);
    }

    @ApiOperation(value = "바코드 번호를 이용한 제품 조회", notes = "바코드 번호를 통해 제품 상세 정보를 제공한다.")
    @PostMapping("/findFood/barcode")
    public ResponseEntity<FoodDetailResponse> findFoodByBarcode(@RequestBody FindFoodByBarcodeRequest request) {
        return ResponseEntity.ok(this.foodService.findFoodByBarcode(request.getBarcode()));
    }

    @ApiOperation(value = "조건에 맞는 제품 목록을 반환", notes = "조건에 맞는 제품을 검색하여 해당하는 제품 목록을 반환해준다")
    @GetMapping("/searchFood")
    public ResponseEntity<Pagination<List<FoodResponse>>> searchFood(
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false) String sortElement,
            @RequestParam(name = "order", defaultValue = "asc") String order,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "foodName", required = false) String foodName,
            @RequestParam(name = "manufacturerName", required = false) String manufacturerName,
            @RequestParam(name = "allergies", required = false) List<String> allergyList) {
        return ResponseEntity.ok(this.foodService.searchFood(
                pageNo, size, sortElement, order, category, foodName, manufacturerName, allergyList));
    }

    @ApiOperation(value = "대분류 카테고리에 해당하는 제품 조회", notes = "대분류 카테고리에 해당하는 제품을 제공한다.")
    @GetMapping("/list/widecategory")
    public ResponseEntity<Pagination<List<FoodResponse>>> getFoodListByWideCategory(@RequestParam String category
            , @RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String sort
            , @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(this.foodService.findFoodByWideCategory(category, page, sort, size));
    }

    //    @ApiOperation(value = "HACCP API를 이용한 food 테이블 생성", notes = "HACCP API로 데이터를 제공받아 데이터베이스의 food 테이블에 데이터를 생성")
//    @GetMapping("/makeFoodDB")
//    public int makeFoodDB() {
//        return this.foodService.makeFoodDatabaseWithoutBarCodeAPI();
//    }

}
