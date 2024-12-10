package com.example.NotarizationBlockchain.config;

import com.example.NotarizationBlockchain.mapper.AppMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public AppMapper appMapper() {
        return Mappers.getMapper(AppMapper.class);
    }
}

