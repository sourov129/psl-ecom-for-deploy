package com.gach.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Global configuration for Cross-Origin Resource Sharing (CORS).
 * This allows the frontend (running on a different port/domain) to communicate with the backend.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Define all allowed origins, including development and production URLs.
        // Since allowCredentials(true) is set, we must list them explicitly.
        String[] allowedOrigins = {
                "http://localhost:5173",
        };

        registry.addMapping("/**") // Apply this policy to all endpoints
                .allowedOrigins(allowedOrigins) // Use the array of allowed domains
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true) // Required for sending cookies/JWTs
                .maxAge(3600);
    }
}