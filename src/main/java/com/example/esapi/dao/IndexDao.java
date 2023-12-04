package com.example.esapi.dao;

import com.example.esapi.mdl.Index;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class IndexDao {

    private final ElasticsearchOperations elasticsearchTemplate;

    public Index add(Index indexDefinition) {
        boolean created = elasticsearchTemplate.indexOps(IndexCoordinates.of(indexDefinition.getName()))
                .create(indexDefinition.getSettings());
        if (!created)
            throw new RuntimeException(String.format("The index '%s' was not created", indexDefinition.getName()));
        return indexDefinition;
    }

}
