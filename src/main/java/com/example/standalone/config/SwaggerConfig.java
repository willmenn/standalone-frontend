package com.example.standalone.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket apiConfig() {
        return new Docket(SWAGGER_2)
                .select()
                .apis(basePackage("com.example.standalone"))
                .build();
    }
}