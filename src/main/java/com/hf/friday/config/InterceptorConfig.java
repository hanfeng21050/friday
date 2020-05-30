package com.hf.friday.config;

import com.hf.friday.interceptor.ComicInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author CoolWind
 * @Date 2020/4/29 18:09
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ComicInterceptor()).addPathPatterns("/card/add").addPathPatterns("/comic/app/**").excludePathPatterns("/comic/app/login");
    }
}
