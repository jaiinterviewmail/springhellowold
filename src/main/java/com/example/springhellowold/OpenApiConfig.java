package com.example.springhellowold;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Product Management API", description = "CRUD operations for Product resource", version = "v1")
)
public class OpenApiConfig {
    // No implementation required - annotation-driven configuration
}

