package com.kati.core.domain.favorite.api;

import com.kati.core.domain.favorite.dto.FavoriteResponse;
import com.kati.core.domain.favorite.service.FavoriteService;
import com.kati.core.global.config.security.auth.PrincipalDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Api(tags = {"Favorite"})
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user/favorite")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @ApiOperation(value = "로그인한 사용자의 즐겨찾기 목록 반환", notes = "로그인한 사용자의 즐켜찾기 목록을 반환한다")
    @GetMapping("/list")
    public List<FavoriteResponse> getFavoriteList(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return this.favoriteService.findAll(principalDetails);
    }

    @ApiOperation(value = "로그인한 사용자의 즐겨찾기 추가", notes = "로그인한 사용자가 선택한 제품을 즐겨찾기에 추가한다")
    @PostMapping("/addFavorite")
    public ResponseEntity<String> addFavoriteFood(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam(name = "foodId") Long foodId) {
        this.favoriteService.add(principalDetails, foodId);
        return ResponseEntity.ok("즐겨찾기 추가 성공");
    }

    @ApiOperation(value = "로그인한 사용자의 즐겨찾기 해제", notes = "로그인한 사용자가 선택한 제품을 즐겨찾기에서 해제한다")
    @DeleteMapping("/deleteFavorite")
    public void deleteFavoriteFood(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam(name = "foodId") Long foodId) {
        this.favoriteService.delete(principalDetails, foodId);
    }

    @ApiOperation(value = "해당 제품의 즐겨찾기 여부 판단", notes = "해당제품이 사용자가 즐겨찾기한 제품인지 아닌지를 반환한다")
    @GetMapping("/checkFavorite")
    public boolean getFavoriteStateForSpecificFood(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam(name = "foodId") Long foodId) {
        return this.favoriteService.getFavoriteStateForSpecificFood(principalDetails, foodId);
    }

}
