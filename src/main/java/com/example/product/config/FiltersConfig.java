package com.example.product.config;

import com.example.product.filter.RequestResponseLoggers;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FiltersConfig {

    @Bean
    FilterRegistrationBean<RequestResponseLoggers> createLoggers(RequestResponseLoggers requestResponseLoggers)
    {
        FilterRegistrationBean<RequestResponseLoggers> registrationBean=new FilterRegistrationBean<RequestResponseLoggers>();
        registrationBean.setFilter(requestResponseLoggers);
        registrationBean.addUrlPatterns("/v1/addproduct","/v1/product/*");
        return registrationBean;
    }
}
