package com.practice.shopping.domain.config;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Configuration
@AllArgsConstructor
public class ShopBeanConfig {
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
