package com.bkbwongo.core.ebaasa.base.config;

import okhttp3.OkHttpClient;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;

/**
 * @author bkaaron
 * @created on 23/04/2021
 * @project ebaasa-sms
 */

@Configuration
public class AppConfig {

    @Bean
    @ApplicationScope
    public OkHttpClient httpClient(){
        return new OkHttpClient();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
