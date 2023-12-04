package com.example.esapi.mdl;

import lombok.Data;

import java.util.Map;

@Data
public class Index {

    private String name;
    private Map<String, Object> settings;

}
