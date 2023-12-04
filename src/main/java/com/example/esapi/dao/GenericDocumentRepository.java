package com.example.esapi.dao;

import com.example.esapi.mdl.GenericDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenericDocumentRepository extends ElasticsearchRepository<GenericDocument, String> {

}
