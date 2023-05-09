package com.example.kunuz.config;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SecuredFilterConfig {
    @Autowired
    private TokenFilter tokenFilter;
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(tokenFilter);
        bean.addUrlPatterns("/api/v1/article/private/*");
        bean.addUrlPatterns("/api/v1/tag/private/*");
        bean.addUrlPatterns("/api/v1/articleType/private/*");
        bean.addUrlPatterns("/api/v1/region/private/*");
        bean.addUrlPatterns("/api/v1/category/private/*");
        bean.addUrlPatterns("/api/v1/*/private/*");
        return bean;
    }
}
