package com.gach.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Paths;

/**
 * Configure static resource serving for uploaded files.
 * This allows files uploaded to /uploads directory to be served via HTTP.
 */
@Configuration
public class FileServeConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve uploaded files from /uploads directory
        String uploadDir = Paths.get(System.getProperty("user.dir"), "uploads").toUri().toString();
        
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDir)
                .setCachePeriod(3600); // Cache for 1 hour
    }
}
