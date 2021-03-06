package com.kati.core.domain.food.service;

import com.kati.core.domain.food.domain.FoodCategory;
import com.kati.core.domain.food.exception.NotFoundFoodException;
import com.kati.core.global.dto.Pagination;
import com.kati.core.domain.food.domain.Food;
import com.kati.core.domain.food.dto.FoodDetailResponse;
import com.kati.core.domain.food.dto.FoodResponse;
import com.kati.core.domain.food.exception.FoodExceptionMessage;
import com.kati.core.domain.food.exception.NoFoodDetailException;
import com.kati.core.domain.food.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FoodServiceImpl implements FoodService {

//    private final ApiKeyRepository apiKeyRepository;
//    private final RestTemplate restTemplate;
    private final FoodRepository foodRepository;

    @Override
    public Food findById(Long foodId) {
        return this.foodRepository.findById(foodId).orElseThrow(NotFoundFoodException::new);
    }

    @Transactional
    @Override
    public FoodDetailResponse getFoodDetail(Long foodId) {
        return FoodDetailResponse.from(this.findById(foodId).viewCountUp());
    }

    @Override
    public FoodDetailResponse findFoodByBarcode(String barcode) {
        return FoodDetailResponse.from(this.foodRepository.findByBarcodeNumber(barcode).orElseThrow(NotFoundFoodException::new));
    }

    @Override
    public Pagination<List<FoodResponse>> searchFood(
            int pageNo, int size, String sortElement, String order, String category,
            String foodName, String manufacturerName, List<String> allergyList
    ) {
        Pageable pageable = PageRequest.of(pageNo - 1, size);
        Page<Food> page = this.foodRepository.search(sortElement, category, foodName, manufacturerName, allergyList, order, pageable);
        List<FoodResponse> data = page.stream().map(FoodResponse::from).collect(Collectors.toList());
        return Pagination.of(page, data);
    }

    @Override
    public Pagination<List<FoodResponse>> findFoodByWideCategory(String category, int page, String sort, int size) {
        Pageable pageable = sort != null ? PageRequest.of(page - 1, size, Sort.by(sort)) : PageRequest.of(page - 1, size);
        String[] categories = FoodCategory.getCategoryList(category).toArray(new String[0]);
        Page<Food> foodPage = this.foodRepository.findByWideCategory(categories, pageable);
        List<FoodResponse> data = foodPage.stream().map(FoodResponse::from).collect(Collectors.toList());
        return Pagination.of(foodPage, data);
    }

//    @SneakyThrows
//    @Override
//    public int makeFoodDatabaseWithoutBarCodeAPI() {
//        for (int t = 0; t < 154; t++) {
//            String url = haccpdataURL + "&pageNo=" + (t + 1);
//            URI uri = new URI(url); // service key % -> 25 encoding ??????
//            String jsonString = restTemplate.getForObject(uri, String.class);
//
//            JsonParser parser = new JsonParser();
//            JsonObject object = parser.parse(jsonString).getAsJsonObject();
//            JsonArray array = object.get("list").getAsJsonArray();
//
//            for (int i = 0; i < array.size(); i++) {
//                JsonObject o = array.get(i).getAsJsonObject();
//                this.foodRepository.save(Food.builder()
//                                             .foodName(getJsonData(o, "prdlstNm"))
//                                             .reportNumber(getJsonData(o, "prdlstReportNo"))
//                                             .category(getJsonData(o, "prdkind"))
//                                             .manufacturerName(getJsonData(o, "manufacture"))
//                                             .foodDetail(FoodDetail.builder()
//                                                                   .materials(getJsonData(o, "rawmtrl"))
//                                                                   .nutrient(getJsonData(o, "nutrient"))
//                                                                   .capacity(getJsonData(o, "capacity"))
//                                                                   .build())
//                                             .foodImage(FoodImage.builder().foodImageAddress(getJsonData(o, "imgurl1"))
//                                                                 .foodMeteImageAddress(getJsonData(o, "imgurl2")).build())
//                                             .allergyMaterials(getJsonData(o, "allergy"))
//                                             .barcodeNumber(getJsonData(o, "barcode"))
//                                             .build());
//            }
//        }
//        return 1;
//    }

//    private String getJsonData(JsonObject o, String key) {
//        JsonElement reportNoObject = o.get(key);
//        String result = "No data";
//        if (reportNoObject != null) {
//            result = reportNoObject.getAsString();
//        }
//        return result;
//    }

}
