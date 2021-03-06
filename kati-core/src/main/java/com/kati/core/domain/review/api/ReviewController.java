package com.kati.core.domain.review.api;

import com.kati.core.domain.review.domain.Review;
import com.kati.core.domain.review.dto.*;
import com.kati.core.domain.review.service.ReviewService;
import com.kati.core.global.config.security.auth.PrincipalDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"Review"})
@RequiredArgsConstructor
@RestController
public class ReviewController {
    private final ReviewService reviewService;

    @ApiOperation(value = "리뷰 생성", notes = "로그인한 사용자가 리뷰를 작성한다.")
    @PostMapping("api/v1/user/createReview")
    public ResponseEntity<CreateReviewResponse> createReview(@AuthenticationPrincipal PrincipalDetails principal,
                                                             @Valid @RequestBody CreateReviewRequest dto) {
        reviewService.saveReview(principal, dto);
        return ResponseEntity.ok(CreateReviewResponse.from(dto));
    }

    @ApiOperation(value = "식품 리뷰 불러오기", notes = "상품에 작성된 리뷰들을 불러온다.")
    @GetMapping("readReview")
    public Map<String, Object> readReview(@RequestParam Long foodId, @RequestParam Integer pageNum) {
        ReadSummaryResponse readSummaryResponse = reviewService.findReviewSummary(foodId);
        List<ReadReviewResponse> reviewList = reviewService.findReview(foodId, pageNum);

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("readSummaryResponse", readSummaryResponse);
        returnMap.put("readReviewResponse", reviewList);

        return returnMap;
    }

    @ApiOperation(value = "식품 리뷰 불러오기", notes = "로그인한 사용자가 상품에 작성된 리뷰들을 불러온다.")
    @GetMapping("api/v1/user/readReview")
    public Map<String, Object> readLoggedInReview(@AuthenticationPrincipal PrincipalDetails principal,
                                                  @RequestParam Long foodId, @RequestParam Integer pageNum) {
    	ReadSummaryResponse readSummaryResponse = reviewService.findReviewSummary(foodId);
        List<ReadReviewResponse> reviewList = reviewService.findReviewByUserIdANDFoodID(principal, foodId, pageNum);

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("readSummaryResponse", readSummaryResponse);
        returnMap.put("readReviewResponse", reviewList);
        return returnMap;
    }

    @ApiOperation(value = "리뷰 정보 불러오기", notes = "로그인한 사용자가 리뷰를 수정할때 정보를 받아올 수 있게")
    @GetMapping("api/v1/user/readReviewByReviewID")
    public ResponseEntity<ReadReviewIdResponse> readReviewByReviewID(@AuthenticationPrincipal PrincipalDetails principal, @RequestParam Long reviewId){
    	return ResponseEntity.ok(reviewService.findReviewByReviewId(principal, reviewId));
    }
    
    @ApiOperation(value = "유저 리뷰들 불러오기", notes = "로그인한 사용자가 작성한 리뷰들을 마이페이지에서 볼 수 있다.")
    @GetMapping("api/v1/user/readReviewByUserID")
    public Map<String, Object> readReviewByUserID(@AuthenticationPrincipal PrincipalDetails principal,
    		@RequestParam(name = "pageNum", required = false) Integer pageNo) {
    	Map<String, Object> returnMap = new HashMap<>();
    	
    	List<ReadReviewResponse> userReviewList = reviewService.findReviewByUserId(principal, pageNo);
    	int userReviewCount = reviewService.findUserReviewCount(principal);
    	returnMap.put("userReviewList", userReviewList);
    	returnMap.put("userReviewCount", userReviewCount);
    	return returnMap;
    }

    @ApiOperation(value = "리뷰에 좋아요 누르기", notes = "로그인한 사용자가 리뷰들에 좋아요를 누를 수 있다.")
    @PostMapping("api/v1/user/updateReviewLike")
    public Map<String, String> updateReviewLike(@AuthenticationPrincipal PrincipalDetails principal, @RequestBody UpdateReviewLikeRequest dto) {
        reviewService.changeReviewLike(principal, dto);

        Map<String, String> returnMap = new HashMap<String, String>();
        if (!dto.isLikeCheck())
            returnMap.put("message", "좋아요를 눌렀습니다.");
        else
            returnMap.put("message", "좋아요를 취소합니다.");
        return returnMap;
    }


    @ApiOperation(value = "리뷰 업데이트", notes = "로그인한 사용자가 작성한 리뷰를 수정한다.")
    @PostMapping("api/v1/user/updateReview")
    public ResponseEntity<UpdateReviewResponse> updateReview(@AuthenticationPrincipal PrincipalDetails principal, @RequestBody UpdateReviewRequest dto) {
        Review reivew = reviewService.changeReview(principal, dto);
        return ResponseEntity.ok(UpdateReviewResponse.builder()
                .message("리뷰 변경 완료")
                .build());
    }

    @ApiOperation(value = "리뷰 삭제", notes = "로그인한 사용자가 작성한 리뷰를 삭제한다.")
    @PostMapping("api/v1/user/deleteReview")
    public ResponseEntity<DeleteReviewResponse> deleteReview(@AuthenticationPrincipal PrincipalDetails principal, @RequestBody DeleteReviewRequest dto) {
        Review reivew = reviewService.removeReview(principal, dto);
        return ResponseEntity.ok(DeleteReviewResponse.builder()
                .message("리뷰 삭제 완료")
                .build());
    }

    @ApiOperation(value = "상품 리뷰 별점순 랭킹", notes = "상품에 매겨진 별점을 평균내서 순위를 매긴다.")
    @GetMapping("reviewRanking")
    public List<ReviewRankingResponse> readReviewRanking() {
        return reviewService.rankedReview();
    }
}
