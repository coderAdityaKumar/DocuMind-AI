package com.aditya.documind.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aditya.documind.Entity.Document;
import com.aditya.documind.Service.DocumentService;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService){
        this.documentService=documentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Document> upload(@RequestParam("file") MultipartFile file){
        Document document=new Document();
        try {
            document=documentService.uploadDocument(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        return ResponseEntity.ok(document);
    }
}
