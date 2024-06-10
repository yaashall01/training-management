package com.revolversolutions.trainingmanagement.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@OpenAPIDefinition
public class OpenApiConfig {

    @Bean
    public OpenAPI baseOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Training Management Project OpenAPI Docs")
                        .version("1.0.0")
                        .description("The Training Management System is a comprehensive web-based application designed to streamline the management of training programs. Leveraging the robust capabilities of the Spring Framework, this project aims to provide an efficient and scalable solution for organizing, tracking, and administering training sessions, participants, and instructors."));
    }

}