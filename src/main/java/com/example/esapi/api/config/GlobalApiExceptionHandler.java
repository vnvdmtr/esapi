package com.example.esapi.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalApiExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleCustomException(Throwable ex) {
        log.error("An exception has occurred {}", ex.getMessage(), ex);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("message", ex.getMessage());
        body.put("class", ex.getClass().getCanonicalName());
        return new ResponseEntity<>(body, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
