package com.meli.dnaanalyzer;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ConfigurationApp {

    @Bean
    private Gson gson() {
        return new Gson();
    }
}