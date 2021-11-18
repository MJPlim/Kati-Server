package com.kati.core.domain.advertisement.api;

import com.kati.core.domain.advertisement.dto.AdvertisementResponse;
import com.kati.core.domain.advertisement.service.AdvertisementService;
import com.kati.core.domain.food.dto.FoodDetailResponse;
import com.kati.core.global.config.security.auth.PrincipalDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Api(tags = {"Advertisement"})
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/advertisement")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @ApiOperation(value = "size 만큼의 랜덤한 광고제품들을 반환한다.")
    @GetMapping("/search")
    public ResponseEntity<List<AdvertisementResponse>> getRandomItems(@RequestParam @Min(1) int size) {
        return ResponseEntity.ok(this.advertisementService.getRandomAdvertisementFoods(size).stream()
                .map(AdvertisementResponse::from)
                .collect(Collectors.toList()));
    }

    @ApiOperation(value = "광고제품에 대한 상세 내용을 반환한다.")
    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(AdvertisementResponse.from(this.advertisementService.findById(id)));
    }

    @ApiOperation(value = "해당 제품을 광고로 등록한다.", notes = "ad_food 테이블에 광고할 제품을 넣는다")
    @PostMapping
    public ResponseEntity<String> saveByFoodId(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam Long foodId) {
        this.advertisementService.saveByFoodId(principalDetails, foodId);
        return ResponseEntity.ok("해당 제품을 광고로 등록 완료했습니다.");
    }

}