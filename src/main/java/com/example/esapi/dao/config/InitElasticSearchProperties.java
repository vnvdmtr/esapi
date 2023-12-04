package com.example.esapi.dao.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitElasticSearchProperties {

    @Bean
    @ConfigurationProperties(prefix = "esapi.elasticsearch")
    public ElasticSearchProperties elasticSearchProperties() {
        return new ElasticSearchProperties();
    }

}
