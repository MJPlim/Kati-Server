package com.kati.gatewayservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class CorsConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowCredentials(true)
                .allowedOrigins(CorsConfiguration.ALL)
                .allowedHeaders(CorsConfiguration.ALL)
                .allowedMethods(CorsConfiguration.ALL)
                .exposedHeaders(CorsConfiguration.ALL);
    }
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
////                headers.add("Access-Control-Allow-Methods", "*")
//                headers.setAccessControlAllowMethods(ImmutableList.of(POST, DELETE, TRACE, HEAD, GET, OPTIONS, PATCH, PUT));
////                headers.add("Access-Control-Allow-Headers", "*");
//                headers.setAccessControlExposeHeaders(ImmutableList.of("*"));
//                headers.add("Access-Control-Max-Age", MAX_AGE);
//                headers.setAccessControlAllowCredentials(true);
//                if (request.getMethod() == OPTIONS) {
//                    response.setStatusCode(HttpStatus.OK);
//                    return Mono.empty();
//                }
//            }
//            return chain.filter(ctx);
//        };
//    }

}
