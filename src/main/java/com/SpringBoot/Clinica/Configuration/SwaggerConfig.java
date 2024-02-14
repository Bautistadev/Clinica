package com.SpringBoot.Clinica.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
public class SwaggerConfig {

    @Bean
    @Primary
    public OpenAPI customSecurityOpenApi() {
        return new OpenAPI()
                .components(
                        new Components().
                                addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("Bearer")
                                        .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"))
                .info(new Info()
                        .title("Clinica")
                        .version("1.0.0")
                        .description("Api clinica")
                        .contact(new Contact()
                                .name("Bautista Basilio")
                                .email("Bautibasilio@hotmail.com"))
                );

    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.SpringBoot.Clinica.Controller;"))
                .paths(PathSelectors.any())
                .build();

    }
}
