package com.example.esapi.mdl;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.HashMap;
import java.util.Map;

@Data
@Document(indexName = "#{@requestIndexProvider.getIndexName()}", createIndex = false)
public class GenericDocument {

    @Id
    private String id;

    @JsonIgnore
    @Field(type = FieldType.Object)
    private Map<String, Object> dynamicFields;

    @JsonAnySetter
    public void addOtherFields(String key, Object value) {
        if (this.dynamicFields == null) {
            this.dynamicFields = new HashMap<>();
        }
        this.dynamicFields.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> retrieveOtherFields() {
        return dynamicFields;
    }

}
