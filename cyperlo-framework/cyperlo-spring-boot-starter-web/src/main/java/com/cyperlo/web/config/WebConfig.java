package com.cyperlo.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.cyperlo.web.aspect.RequestLogControllerAspect;

@Configuration
public class WebConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public RequestLogControllerAspect requestLogControllerAspect() {
        return new RequestLogControllerAspect();
    }
}
