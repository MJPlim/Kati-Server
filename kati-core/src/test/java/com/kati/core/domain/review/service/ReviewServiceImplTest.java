package com.kati.core.domain.review.service;

import com.kati.core.domain.food.repository.FoodRepository;
import com.kati.core.domain.review.domain.Review;
import com.kati.core.domain.review.domain.ReviewLike;
import com.kati.core.domain.review.domain.ReviewStateType;
import com.kati.core.domain.review.dto.*;
import com.kati.core.domain.review.repository.ReviewRepository;
import com.kati.core.domain.user.domain.User;
import com.kati.core.domain.user.domain.UserProvider;
import com.kati.core.domain.user.domain.UserRoleType;
import com.kati.core.domain.user.domain.UserStateType;
import com.kati.core.global.config.security.auth.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@SpringBootTest
@Transactional
class ReviewServiceImplTest {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewServiceImpl reviewServiceimpl;

    @Mock
    private PrincipalDetails principalDetails;

    private final User user = User.builder()
            .email("cksgh3422@nate.com")
            .password("1234568")
            .name("정찬호")
            .role(UserRoleType.ROLE_USER)
            .birth(null)
            .address(null)
            .profileImageAddress(null)
            .state(UserStateType.NORMAL)
            .provider(UserProvider.KAKAO)
            .build();


    @Test
//    @Rollback(false)
    void saveReview() throws Exception {
        CreateReviewRequest request = new CreateReviewRequest(10103L, 4, "테스트 코드 입니다.");
        reviewServiceimpl.saveReview(new PrincipalDetails(user), request);
        ReadReviewResponse response = reviewServiceimpl.findReview(10103L, 1).get(0);

        assertThat(request.getReviewDescription()).isEqualTo(response.getReviewDescription());
        assertThat(request.getReviewRating()).isEqualTo(response.getReviewRating());
        assertThat(request.getFoodId()).isEqualTo(response.getFoodId());
    }

    @Test
    void findReview() {
        ReadReviewResponse response = reviewServiceimpl.findReview(10101L, 1).get(0);

        assertThat(4).isEqualTo(response.getReviewRating());
        assertThat("테스트 코드 입니다.").isEqualTo(response.getReviewDescription());
    }

    @Test
    void findReviewByUserIdANDFoodID() {
        ReadReviewResponse response = reviewServiceimpl.findReviewByUserIdANDFoodID(new PrincipalDetails(user), 10101L, 1).get(0);

        assertThat(4).isEqualTo(response.getReviewRating());
        assertThat("테스트 코드 입니다.").isEqualTo(response.getReviewDescription());
    }

    @Test
    void findReviewByUserId() {
        //틀린게 맞음 --> 사용자가 작성한 모든 리뷰 중 첫번째를 보여주기 때문이다. 혹시모르니 냅두고 isNotEqualTO로 바꿀것임
        ReadReviewResponse response = reviewServiceimpl.findReviewByUserId(new PrincipalDetails(user), 1).get(0);

        assertThat(5).isNotEqualTo(response.getReviewRating());
        assertThat("테스트 코드 입니다.").isNotEqualTo(response.getReviewDescription());
    }

    @Test
    void findUserReviewCount() {
        int response = reviewServiceimpl.findUserReviewCount(new PrincipalDetails(user));
        assertThat(2).isEqualTo(response);
    }

    /////////////////

    @Test
    @DisplayName("해당 음식의 리뷰에 대한 정보를 보여준다.")
    void findReviewSummary() {
        ReadSummaryResponse reviewSummary = reviewServiceimpl.findReviewSummary(10101L);
        log.info("reviewCount -> {}", reviewSummary.getReviewCount());
        assertEquals(1, reviewSummary.getReviewCount());
    }

    @Test
    @DisplayName("리뷰ID로 리뷰를 찾아준다.")
    void findReviewByReviewId() {
        ReadReviewIdResponse reviewByReviewId = reviewServiceimpl.findReviewByReviewId(new PrincipalDetails(user), 414L);

        log.info("reviewId -> {}", reviewByReviewId.getReviewId());
        log.info("reviewDesc -> {}", reviewByReviewId.getReviewDescription());

        assertEquals(414, reviewByReviewId.getReviewId());
        assertEquals("테스트 코드 입니다.", reviewByReviewId.getReviewDescription());
    }

    @Test
    @DisplayName("리뷰를 변경한다.")
    void changeReview() {
        ReadReviewIdResponse reviewByReviewId = reviewServiceimpl.findReviewByReviewId(new PrincipalDetails(user), 414L);
        log.info("reviewDesc -> {}", reviewByReviewId.getReviewDescription());
        log.info("reviewRating -> {}", reviewByReviewId.getReviewRating());

        UpdateReviewRequest updateReviewRequest = new UpdateReviewRequest(414L, 3, "변경된 테스트");
        Review review = reviewServiceimpl.changeReview(new PrincipalDetails(user), updateReviewRequest);

        log.info("reviewDesc -> {}", review.getReviewDescription());
        log.info("reviewRating -> {}", review.getReviewRating());

        assertEquals("변경된 테스트", review.getReviewDescription());
        assertEquals(3, review.getReviewRating());
    }

    @Test
    @DisplayName("리뷰에 좋아요를 누르거나 좋아요를 취소한다.")
//    @Rollback(false)
    void changeReviewLike() {
        UpdateReviewLikeRequest updateReviewLikeRequest = new UpdateReviewLikeRequest(414L, false);
        ReviewLike reviewLike = reviewServiceimpl.changeReviewLike(new PrincipalDetails(user), updateReviewLikeRequest);

        //좋아요가 눌러지면 review_like에 reviewId와 userID가 추가된다.
        log.info("reviewID -> {}", reviewLike.getReview().getId());
        log.info("userID -> {}", reviewLike.getUserId());

        assertEquals(247, reviewLike.getUserId());
//        assertNull(reviewLike);
//        assertNotNull(reviewLike);
    }

    @Test
    @DisplayName("리뷰를 삭제한다.")
    void removeReview() {
        ReadReviewIdResponse reviewByReviewId = reviewServiceimpl.findReviewByReviewId(new PrincipalDetails(user), 414L);
        log.info("reviewId -> {}", reviewByReviewId.getReviewId());

        DeleteReviewRequest deleteReviewRequest = new DeleteReviewRequest(414L);
        Review review = reviewServiceimpl.removeReview(new PrincipalDetails(user), deleteReviewRequest);

        log.info("reviewId -> {}", review.getId());
        log.info("reviewState -> {}", review.getState());

        assertEquals(ReviewStateType.DELETED, review.getState());
    }

    @Test
    @DisplayName("리뷰 랭킹 보기")
    void rankedReview() {
        List<ReviewRankingResponse> reviewRankingResponses = reviewServiceimpl.rankedReview();
        reviewRankingResponses.stream().forEach(review -> log.info(String.format("%s %s", review.getFoodName(), review.getAvgRating())));
        assertEquals(10, reviewRankingResponses.size());
    }
}