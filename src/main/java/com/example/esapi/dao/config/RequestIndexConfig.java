package com.example.esapi.dao.config;


import com.example.esapi.dto.ConcreteRequestIndex;
import com.example.esapi.dto.RequestIndex;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class RequestIndexConfig {

    @Bean
    @RequestScope
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public RequestIndex requestIndex() {
        return new ConcreteRequestIndex();
    }

}
