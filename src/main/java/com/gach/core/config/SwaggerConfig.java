package com.gach.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @io.swagger.v3.oas.annotations.info.Info(
        title = "GachCore E-Commerce API",
        version = "1.0.0",
        description = "Complete E-Commerce Platform API with Product Management, Advanced Search, Order Processing, and Image Management",
        contact = @io.swagger.v3.oas.annotations.info.Contact(
            name = "GachCore Team",
            url = "https://gachcore.com",
            email = "support@gachcore.com"
        )
    ),
    tags = {
        @Tag(name = "Products", description = "Product management endpoints"),
        @Tag(name = "Orders", description = "Order management endpoints"),
        @Tag(name = "Authentication", description = "Authentication endpoints")
    }
)
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("GachCore E-Commerce API")
                .version("1.0.0")
                .description("E-Commerce Platform API with Product Search, Image Management, Stock Tracking, and Order Processing")
                .contact(new Contact()
                    .name("GachCore Support")
                    .email("support@gachcore.com")))
            .addServersItem(new Server()
                .url("http://localhost:10011")
                .description("Development Server"));
    }
}