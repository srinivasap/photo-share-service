package com.photo.share;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Swagger Configuration for API spec.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/photos/**"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Photo Share RESTful API",
                "API to upload, download (through sharable url), search, delete photos",
                "0.0.1",
                "",
                new Contact("Srinivasa Prasad S", "", ""),
                "", "", Collections.emptyList()
        );
    }
}
