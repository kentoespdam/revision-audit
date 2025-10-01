package com.kentoes.revisionaudit.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Configuration
@EnableWebMvc
public class OpenApiConfig {
    private SecurityScheme getSecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }

    private Components getComponents() {
        return new Components()
                .addSecuritySchemes("BearerAuthentication", getSecurityScheme());
    }

    private Info getInfo() {
        return new Info()
                .title("Revision Audit API")
                .description("API for Revision Audit with JWT Token required")
                .version("1.0.0")
                .contact(
                        new Contact()
                                .name("Bagus Sudrajat")
                                .email("kentoes.pdam@gmail.com")
                                .url("https://github.com/kentoespdam")
                );
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .components(getComponents())
                .security(List.of(
                        new SecurityRequirement()
                                .addList("BearerAuthentication")
                ))
                .info(getInfo());
    }
}
