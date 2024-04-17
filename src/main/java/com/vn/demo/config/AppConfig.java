package com.vn.demo.config;

import com.vn.demo.helper.LocalDateTypeAdapter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public GsonBuilderCustomizer typeAdapterRegistration() {
        return builder -> {
            builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTypeAdapter());
        };
    }
}
