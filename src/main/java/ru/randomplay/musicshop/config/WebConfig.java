package ru.randomplay.musicshop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${app.image.upload-dir}")
    private String uploadDir;

    // Надо, чтобы браузер смог нормально получать файлы не из static
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/products/**")
                .addResourceLocations("file:" + uploadDir + File.separator);
    }
}