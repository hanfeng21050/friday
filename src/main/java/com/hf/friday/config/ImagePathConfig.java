package com.hf.friday.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ImagePathConfig extends WebMvcConfigurerAdapter {
    @Value("${file.staticAccessStandardPath}")
    private String staticAccessStandardPath;//原图的对外暴露的地址

    @Value("${file.staticAccessThumbnailPath}")
    private String staticAccessThumbnailPath;//缩略图的对外暴露的地址

    @Value("${file.standardPath}")
    private String standardPath;

    @Value("${file.thumbnailPath}")
    private String thumbnailPath;

    @Value("${comic.path}")
    private String comicPath;
    @Value("${comic.staticAccesspath}")
    private String staticAccessComicPath;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(staticAccessStandardPath).addResourceLocations("file:///" + standardPath);
        registry.addResourceHandler(staticAccessThumbnailPath).addResourceLocations("file:///" + thumbnailPath);
        registry.addResourceHandler(staticAccessComicPath).addResourceLocations("file:///" + comicPath);

        super.addResourceHandlers(registry);
    }

}
