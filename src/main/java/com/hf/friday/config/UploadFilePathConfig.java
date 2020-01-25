package com.hf.friday.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class UploadFilePathConfig extends WebMvcConfigurerAdapter {
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    @Value("${file.uploadPath}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:///" + uploadPath);
        super.addResourceHandlers(registry);
    }

}
