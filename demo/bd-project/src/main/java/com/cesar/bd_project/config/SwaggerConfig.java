package com.cesar.bd_project.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
 
import io.swagger.v3.oas.models.OpenAPI;
 
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new io.swagger.v3.oas.models.info.Info()
            .title("API")
            .version("v1"));
     }
}
