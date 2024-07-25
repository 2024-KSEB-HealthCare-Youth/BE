package com.keb.fmhj.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedOrigins("http://localhost:8080")
                .allowedOrigins("http://52.79.103.61:3000")
                .allowedOrigins("http://52.79.103.61:8080");
    }
}
