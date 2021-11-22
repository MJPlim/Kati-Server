package com.kati.gatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {
//public class CorsConfig implements WebFluxConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
////                .allowCredentials(true)
//                .allowedOrigins(CorsConfiguration.ALL)
//                .allowedHeaders(CorsConfiguration.ALL)
//                .allowedMethods(CorsConfiguration.ALL)
//                .exposedHeaders(CorsConfiguration.ALL);
//    }
//
//    @Order(0)
//    @Bean
//    public CorsWebFilter corsWebFilter() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowCredentials(false);
//        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
//        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
////        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
//        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
//        corsConfiguration.addExposedHeader(CorsConfiguration.ALL);
//        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//        return new CorsWebFilter(corsConfigurationSource);
//    }
//
//    private static final String ALLOWED_HEADERS = "x-requested-with, authorization, Content-Type, Content-Length, Authorization, credential, X-XSRF-TOKEN";
//    private static final String ALLOWED_ORIGIN = "*";
//    private static final String MAX_AGE = "7200"; //2 hours (2 * 60 * 60)
//

//    @Bean
//    public WebFilter corsFilter() {
//        return (ServerWebExchange ctx, WebFilterChain chain) -> {
//            ServerHttpRequest request = ctx.getRequest();
//            if (CorsUtils.isCorsRequest(request)) {
//                ServerHttpResponse response = ctx.getResponse();
//                HttpHeaders headers = response.getHeaders();
////                headers.add("Access-Control-Allow-Origin", "*");
//                headers.setAccessControlAllowOrigin("*");
//                headers.setAccessControlExposeHeaders(ImmutableList.of("*"));
//                headers.add("Access-Control-Allow-Methods", "*");
////                headers.add("Access-Control-Allow-Headers", "*");
//                headers.setAccessControlAllowCredentials(true);
//                if (request.getMethod() == HttpMethod.OPTIONS) {
//                    response.setStatusCode(HttpStatus.OK);
//                    return Mono.empty();
//                }
//            }
//            return chain.filter(ctx);
//        };
//    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.setMaxAge(7200L);
        corsConfiguration.addAllowedMethod(HttpMethod.GET);
        corsConfiguration.addAllowedMethod(HttpMethod.POST);
        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
        corsConfiguration.addAllowedMethod(HttpMethod.OPTIONS);
        corsConfiguration.addAllowedMethod(HttpMethod.HEAD);
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addExposedHeader("*");
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(urlBasedCorsConfigurationSource);
    }

}
