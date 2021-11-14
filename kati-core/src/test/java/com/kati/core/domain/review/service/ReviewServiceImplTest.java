package com.kati.core.domain.review.service;

import com.kati.core.domain.food.repository.FoodRepository;
import com.kati.core.domain.review.dto.CreateReviewRequest;
import com.kati.core.domain.review.dto.ReadReviewResponse;
import com.kati.core.domain.review.repository.ReviewRepository;
import com.kati.core.domain.user.domain.User;
import com.kati.core.domain.user.domain.UserProvider;
import com.kati.core.domain.user.domain.UserRoleType;
import com.kati.core.domain.user.domain.UserStateType;
import com.kati.core.global.config.security.auth.PrincipalDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
class ReviewServiceImplTest {

//    @Autowired
//    private FoodRepository foodRepository;
//
//    @Autowired
//    private ReviewRepository reviewRepository;
//
//    @Autowired
//    private ReviewServiceImpl reviewServiceimpl;
//
////    @Autowired
////    private ObjectMapper objectMapper;
//
//    private final User user = User.builder()
//            .email("cksgh3422@nate.com")
//            .password("1234568")
//            .name("정찬호")
//            .role(UserRoleType.ROLE_USER)
//            .birth(null)
//            .address(null)
//            .profileImageAddress(null)
//            .state(UserStateType.NORMAL)
//            .provider(UserProvider.KAKAO)
//            .build();
//
//
//    @Test
//    void saveReview() throws Exception {
//        CreateReviewRequest request = new CreateReviewRequest(10101L, 4, "테스트 코드 입니다.");
//        reviewServiceimpl.saveReview(new PrincipalDetails(user), request);
//        ReadReviewResponse response = reviewServiceimpl.findReview(10101L, 1).get(0);
//
//        assertThat(request.getReviewDescription()).isEqualTo(response.getReviewDescription());
//        assertThat(request.getReviewRating()).isEqualTo(response.getReviewRating());
//        assertThat(request.getFoodId()).isEqualTo(response.getFoodId());
//    }
//
//    @Test
//    void findReview() {
//        CreateReviewRequest request = new CreateReviewRequest(10101L, 4, "테스트 코드 입니다.");
//        reviewServiceimpl.saveReview(new PrincipalDetails(user), request);
//
//        ReadReviewResponse response = reviewServiceimpl.findReview(10101L, 1).get(0);
//
//        assertThat(request.getReviewRating()).isEqualTo(response.getReviewRating());
//        assertThat(request.getReviewDescription()).isEqualTo(response.getReviewDescription());
//    }
//
//    @Test
//    void findReviewByUserIdANDFoodID() {
//        CreateReviewRequest request = new CreateReviewRequest(10101L, 4, "테스트 코드 입니다.");
//        reviewServiceimpl.saveReview(new PrincipalDetails(user), request);
//
//        ReadReviewResponse response = reviewServiceimpl.findReviewByUserIdANDFoodID(new PrincipalDetails(user), request.getFoodId(), 1).get(0);
//
//        assertThat(request.getReviewRating()).isEqualTo(response.getReviewRating());
//        assertThat(request.getReviewDescription()).isEqualTo(response.getReviewDescription());
//    }
//
//    @Test
//    void findReviewByUserId() {
//        CreateReviewRequest request = new CreateReviewRequest(10101L, 4, "테스트 코드 입니다.");
//        reviewServiceimpl.saveReview(new PrincipalDetails(user), request);
//
//        //틀린게 맞음 --> 사용자가 작성한 모든 리뷰 중 첫번째를 보여주기 때문이다. 혹시모르니 냅두고 isNotEqualTO로 바꿀것임
//        ReadReviewResponse response = reviewServiceimpl.findReviewByUserId(new PrincipalDetails(user), 1).get(0);
//
//        assertThat(request.getReviewRating()).isEqualTo(response.getReviewRating());
//        assertThat(request.getReviewDescription()).isEqualTo(response.getReviewDescription());
//    }
//
//    @Test
//    void findUserReviewCount() {
//        CreateReviewRequest request = new CreateReviewRequest(10101L, 4, "테스트 코드 입니다.");
//        reviewServiceimpl.saveReview(new PrincipalDetails(user), request);
//
//        int response = reviewServiceimpl.findUserReviewCount(new PrincipalDetails(user));
//
//        assertThat(1).isEqualTo(response);
//    }
//
//    @Test
//    void findReviewSummary() {
//    }
//
//    @Test
//    void findReviewByReviewId() {
//    }
//
//    @Test
//    void changeReview() {
//    }
//
//    @Test
//    void changeReviewLike() {
//    }
//
//    @Test
//    void removeReview() {
//    }
//
//    @Test
//    void rankedReview() {
//    }
}