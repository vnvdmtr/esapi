package com.example.esapi.dao.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.lang.NonNullApi;

import java.util.Objects;

@AllArgsConstructor
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.esapi.dao")
@ComponentScan(basePackages = { "com.example.esapi" })
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    private final ElasticSearchProperties elasticSearchProperties;

    @Override
    public ClientConfiguration clientConfiguration() {
        ClientConfiguration.MaybeSecureClientConfigurationBuilder builder= ClientConfiguration.builder()
                .connectedTo(elasticSearchProperties.getHosts().toArray(new String[0]));
        ClientConfiguration.TerminalClientConfigurationBuilder terminalBuilder = null;
        if (elasticSearchProperties.getUseSsl()) {
            terminalBuilder = builder.usingSsl();
        }
        if (Objects.isNull(terminalBuilder)) {
            terminalBuilder = builder.withBasicAuth(
                    elasticSearchProperties.getUserName(),
                    elasticSearchProperties.getPassword()
            );
        } else {
            terminalBuilder = terminalBuilder.withBasicAuth(
                    elasticSearchProperties.getUserName(),
                    elasticSearchProperties.getPassword()
            );
        }
        return terminalBuilder.build();
    }
}
