package com.hmh.lab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableWebMvc
@Configuration
public class EtagConfig {

    @Bean
    public ShallowEtagHeaderFilter etagFilter() {
        return new ShallowEtagHeaderFilter();
    }
}