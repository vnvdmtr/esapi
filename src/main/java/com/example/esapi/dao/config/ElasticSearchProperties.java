package com.example.esapi.dao.config;

import lombok.Data;

import java.util.List;

@Data
public class ElasticSearchProperties {

    private List<String> hosts;
    private Boolean useSsl = false;
    private String userName;
    private String password;

}
