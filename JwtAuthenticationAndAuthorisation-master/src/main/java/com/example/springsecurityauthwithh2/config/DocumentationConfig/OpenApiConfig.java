package com.example.springsecurityauthwithh2.config.DocumentationConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Spring Boot 3 Authentication and Authorization")
                        .description("JWT Roles and Permissions Based Authorization")
                        .version("2.0"));
    }

}