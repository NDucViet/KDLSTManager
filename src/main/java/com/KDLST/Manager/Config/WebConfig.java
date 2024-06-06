package com.KDLST.Manager.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/hotel/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/user/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/blog/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/service/**").addResourceLocations("classpath:/static/");

    }
}
