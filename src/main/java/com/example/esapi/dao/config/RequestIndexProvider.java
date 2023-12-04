package com.example.esapi.dao.config;

import com.example.esapi.dto.RequestIndex;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@RequiredArgsConstructor
@Component
public class RequestIndexProvider {

    public static final String defaultIndex = "default_documents";

    private final RequestIndex requestIndex;

    public String getIndexName() {
        if (requestIndex == null)
            return null;
        if (RequestContextHolder.getRequestAttributes() == null)
            return defaultIndex;
        return requestIndex.getName();
    }

}
