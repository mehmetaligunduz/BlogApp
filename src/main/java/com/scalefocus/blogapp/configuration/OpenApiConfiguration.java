package com.scalefocus.blogapp.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openAPI() {

        Info information = new Info()
                .title("Blog Application")
                .version("0.2.0")
                .description("A simple and user-friendly blog application that allows users to create, edit, and share posts. It features a clean interface, secure authentication, and supports multimedia content. Ideal for personal blogs or content sharing platforms.");
        
        return new OpenAPI().info(information);
    }
}
