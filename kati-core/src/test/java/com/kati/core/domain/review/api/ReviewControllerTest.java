package com.kati.core.domain.review.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kati.core.domain.annotation.EnableMockMvc;
import com.kati.core.domain.review.dto.CreateReviewRequest;
import com.kati.core.domain.user.domain.User;
import com.kati.core.domain.user.domain.UserProvider;
import com.kati.core.domain.user.domain.UserRoleType;
import com.kati.core.domain.user.domain.UserStateType;
import com.kati.core.global.config.security.auth.PrincipalDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@EnableMockMvc
@Transactional
class ReviewControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;


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
    void loginTest() throws Exception {
        String email = "cksgh3422@nate.com";
        String password = "1234568";
        String principalUser = objectMapper.writeValueAsString(new UserlogInDto(email, password));
        mvc.perform(post("/login")
                .content(principalUser)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "cksgh3422@nate.com", userDetailsServiceBeanName = "principalDetailsService")
    void createReview() throws Exception {
        String principalUser = objectMapper.writeValueAsString(new PrincipalDetails(user));
        String request = objectMapper.writeValueAsString(new CreateReviewRequest(10102L, 4, "테스트코드입니다."));
        System.out.println(request);
        mvc.perform(post("/api/v1/user/createReview")
                .content(principalUser)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
//                .andExpect(content().json(request));
    }

    @Test
    void readReview() throws Exception {
        mvc.perform(get("/readReview")
                .param("foodId", "14461")
                .param("pageNum", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = "cksgh3422@nate.com", userDetailsServiceBeanName = "principalDetailsService")
    void readLoggedInReview() throws Exception {
        String principalUser = objectMapper.writeValueAsString(new PrincipalDetails(user));
        mvc.perform(get("/api/v1/user/readReview")
                .content(principalUser)
                .param("foodId", "14461")
                .param("pageNum", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = "cksgh3422@nate.com", userDetailsServiceBeanName = "principalDetailsService")
    void readReviewByReviewID() throws Exception {
        String principalUser = objectMapper.writeValueAsString(new PrincipalDetails(user));
        mvc.perform(get("/api/v1/user/readReviewByReviewID")
                .content(principalUser)
                .param("reviewId", "388"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = "cksgh3422@nate.com", userDetailsServiceBeanName = "principalDetailsService")
    void readReviewByUserID() throws Exception {
        String principalUser = objectMapper.writeValueAsString(new PrincipalDetails(user));
        mvc.perform(get("/api/v1/user/readReviewByUserID")
                .content(principalUser)
                .param("reviewId", "388"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = "cksgh3422@nate.com", userDetailsServiceBeanName = "principalDetailsService")
    void updateReviewLike() throws Exception {
        Map<Object, String> map = new HashMap<>();
        map.put("reviewId", "77");
        map.put("likeCheck", "true");
        String principalUser = objectMapper.writeValueAsString(new PrincipalDetails(user));
        String dto = objectMapper.writeValueAsString(map);
        mvc.perform(post("/api/v1/user/updateReviewLike")
                .content(principalUser)
                .content(dto)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = "cksgh3422@nate.com", userDetailsServiceBeanName = "principalDetailsService")
    void updateReview() throws Exception {
        Map<Object, String> map = new HashMap<>();
        map.put("reviewId", "388");
        map.put("reviewRating", "4");
        map.put("reviewDescription", "수정");
        String principalUser = objectMapper.writeValueAsString(new PrincipalDetails(user));
        String dto = objectMapper.writeValueAsString(map);
        mvc.perform(post("/api/v1/user/updateReview")
                .content(principalUser)
                .content(dto)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = "cksgh3422@nate.com", userDetailsServiceBeanName = "principalDetailsService")
    void deleteReview() throws Exception {
        Map<Object, String> map = new HashMap<>();
        map.put("reviewId", "388");
        String principalUser = objectMapper.writeValueAsString(new PrincipalDetails(user));
        String dto = objectMapper.writeValueAsString(map);
        mvc.perform(post("/api/v1/user/deleteReview")
                .content(principalUser)
                .content(dto)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void readReviewRanking() throws Exception {
        mvc.perform(get("/reviewRanking"))
                .andExpect(status().isOk())
                .andDo(print());

    }
}


@Getter
@AllArgsConstructor
class UserlogInDto {
    @Email
    private String email;

    @Size(min = 7, max = 20, message = "패스워드는 7글자 이상 20글자 이하여야 합니다.")
    private String password;
}