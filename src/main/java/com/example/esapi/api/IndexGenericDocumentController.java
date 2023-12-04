package com.example.esapi.api;

import com.example.esapi.dao.GenericDocumentRepository;
import com.example.esapi.mdl.GenericDocument;
import com.example.esapi.dto.RequestIndex;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/indices/{index}/documents")
public class IndexGenericDocumentController {

    private final GenericDocumentRepository genericDocumentRepository;
    private final RequestIndex requestIndex;

    @PostMapping
    public GenericDocument createDocument(@PathVariable String index, @RequestBody GenericDocument document) {
        requestIndex.setName(index);
        document.setId(null);
        return genericDocumentRepository.save(document);
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<GenericDocument> getById(@PathVariable String index, @PathVariable String documentId) {
        requestIndex.setName(index);
        return genericDocumentRepository
                .findById(documentId)
                .map(doc -> new ResponseEntity<GenericDocument>(doc, HttpStatusCode.valueOf(200)))
                .orElse(new ResponseEntity<GenericDocument>(HttpStatusCode.valueOf(404))
        );
    }

}
