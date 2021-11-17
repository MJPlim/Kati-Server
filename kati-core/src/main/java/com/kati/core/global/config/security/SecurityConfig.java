package com.kati.core.global.config.security;

import com.google.gson.Gson;
import com.kati.core.domain.user.domain.UserRoleType;
import com.kati.core.global.config.security.auth.PrincipalDetails;
import com.kati.core.global.config.security.auth.PrincipalOauth2DetailsService;
import com.kati.core.global.config.security.exception.CustomAuthenticationEntryPoint;
import com.kati.core.global.config.security.jwt.JwtAuthenticationFilter;
import com.kati.core.global.config.security.jwt.JwtAuthorizationFilter;
import com.kati.core.global.config.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final JwtTokenProvider jwtTokenProvider;
    private final PrincipalOauth2DetailsService principalOauth2DetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(this.corsFilter, SecurityContextPersistenceFilter.class)
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtTokenProvider))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtTokenProvider))
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        http
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(principalOauth2DetailsService)
                .and()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        String token = jwtTokenProvider.createToken(((PrincipalDetails) authentication.getPrincipal()).getUsername());
                        httpServletResponse.addHeader("Authorization", "Bearer " + token);
                        RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher("/oauth-success");
                        requestDispatcher.forward(httpServletRequest, httpServletResponse);
                        System.out.println("토큰 인증 성공 : " + token);
                    }
                }).failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        response.setContentType("application/json;charset=UTF-8");
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        Map<String, String> responseObj = new HashMap<>();
                        responseObj.put("error-message", exception.getMessage());
                        String json = new Gson().toJson(responseObj);
                        response.getWriter().write(json);
                    }
                });

        http
                .authorizeRequests()
                .antMatchers("/api/v1/user/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/admin/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/advertisement/**")
                .hasRole("ADMIN")
                .anyRequest()
                .permitAll();
    }

}
