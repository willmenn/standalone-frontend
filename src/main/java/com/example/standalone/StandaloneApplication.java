package com.example.standalone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableConfigurationProperties
@EnableSwagger2
public class StandaloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(StandaloneApplication.class, args);
    }

}
