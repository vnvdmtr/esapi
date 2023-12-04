package com.example.esapi.api;

import com.example.esapi.dao.IndexDao;
import com.example.esapi.mdl.Index;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/indices")
public class IndexController {

    private final IndexDao indexDao;

    @PostMapping
    public Index add(@RequestBody Index indexDefinition) {
        return indexDao.add(indexDefinition);
    }

}
