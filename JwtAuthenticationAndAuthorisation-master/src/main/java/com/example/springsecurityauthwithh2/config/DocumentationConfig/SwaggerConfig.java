package com.example.springsecurityauthwithh2.config.DocumentationConfig;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

//defines how authentication and authorization is handled when accessing your API
@SecurityScheme(
        name = "bearerAuth",
        //bearer means authentication with token
        scheme = "bearer",
        //JWT - token format
        bearerFormat = "JWT",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {

    @Bean
    //GroupedOpenApi class from Springfox library representing the API group
    //for Swagger/OpenAPI documentation
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("test")
                .pathsToMatch("/api/v1/auth/**")
                .build();
    }
}
