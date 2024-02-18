//package com.example.onlineshoping.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//@Configuration
//public class CorsConfig {
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*"); // Allow requests from any origin (for development purposes, use specific origins in production)
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("GET");
//        config.addAllowedMethod("POST");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
//}
