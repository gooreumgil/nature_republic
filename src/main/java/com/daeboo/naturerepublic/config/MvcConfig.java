package com.daeboo.naturerepublic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/detail").setViewName("/items/detail");
        registry.addViewController("/list").setViewName("/item/index");
        registry.addViewController("/").setViewName("/welcome/index");
    }

}
