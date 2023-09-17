package com.oliber.userapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "REST API", version = "1.0",
    description = "REST API description...",
    contact = @Contact(name = "Name Surname")),
    security = {@SecurityRequirement(name = "bearerToken")}
)
@SecuritySchemes({
    @SecurityScheme(name = "bearerToken", type = SecuritySchemeType.HTTP,
        scheme = "bearer", bearerFormat = "JWT")
})
public class SwaggerConfig {
/*
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.codmind.swaggerapi.controllers"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(getApiInfo())
            ;
    }

    private ApiInfo getApiInfo() {
        springfox.documentation.service.Contact contact = new springfox.documentation.service.Contact(
            "Oliber Garcia",
            "https://codmind.com",
            "");
        return new ApiInfo(
            "User Service API",
            "User Service API for challenge Nisum",
            "1.0",
            "terms",
            contact,
            "LICENSE Oliber",
            "LICENSE URL Oliber",
            Collections.emptyList()
        );
    }
    */
}
