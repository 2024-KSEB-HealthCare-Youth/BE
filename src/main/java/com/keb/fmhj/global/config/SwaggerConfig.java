package com.keb.fmhj.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                .name("JWT Authentication")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .description("access token을 넣어주세요!")))
                .info(apiInfo())
                .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
    }

    private Info apiInfo() {
        return new Info()
                .title("Youth REST API Swagger Test Page")
                .description("made by FMHJ(Face Manager for Happy and Joy)")
                .version("1.0.0");
    }
}