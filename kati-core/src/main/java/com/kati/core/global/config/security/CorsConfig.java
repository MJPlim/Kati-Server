//package com.kati.core.global.config.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//@Configuration
//public class CorsConfig {
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowCredentials(true);
////        configuration.setAllowedOriginPatterns(Collections.singletonList(CorsConfiguration.ALL));
////        configuration.addExposedHeader("*");
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", configuration);
//
//        return new CorsFilter(source);
//    }
//
//}
