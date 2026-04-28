package com.aditya.documind.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aditya.documind.Embedding.EmbeddingService;
import com.aditya.documind.Entity.Document;
import com.aditya.documind.Entity.DocumentChunk;

import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;

import com.aditya.documind.Entity.Organization;
import com.aditya.documind.Repository.DocumentRepo;
import com.aditya.documind.Repository.DocumentChunkRepo;
import com.aditya.documind.Repository.OrganizationRepo;
import com.aditya.documind.Tenant.TenantContext;

@Service
public class DocumentService {
    private final DocumentRepo documentRepo;
    private final OrganizationRepo organizationRepo;
    private final DocumentChunkRepo documentChunkRepo;
    private final EmbeddingService embeddingService;

    public DocumentService(DocumentRepo documentRepo, OrganizationRepo organizationRepo,
            DocumentChunkRepo documentChunkRepo, EmbeddingService embeddingService) {
        this.documentRepo = documentRepo;
        this.organizationRepo = organizationRepo;
        this.documentChunkRepo = documentChunkRepo;
        this.embeddingService = embeddingService;
    }

    public Document uploadDocument(MultipartFile file) throws IOException {

        Long tenantId = TenantContext.getTenantId();

        Organization org = organizationRepo.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Organization id :" + tenantId + " doesn't exist"));

        String content = new String(file.getBytes());

        Document document = Document.builder()
                .name(file.getOriginalFilename())
                .fileType(file.getContentType())
                .createdAt(LocalDateTime.now())
                .organization(org)
                .build();

        document = documentRepo.save(document);

        org.springframework.ai.document.Document aiDoc = new org.springframework.ai.document.Document(content);

        TokenTextSplitter splitter = TokenTextSplitter.builder()
        .withChunkSize(800)           // Target tokens per chunk
        .withMinChunkSizeChars(350)    // Will look for punctuation after 350 chars to split cleanly
        .withKeepSeparator(true)
        .build();

        var chunks = splitter.split(List.of(aiDoc));

        List<DocumentChunk> chunkList = new ArrayList<>();

        for (org.springframework.ai.document.Document chunk : chunks) {
            float[] embedding = embeddingService.generateEmbedding(chunk.getText());
            System.out.println(embedding.length);
            DocumentChunk documentChunk = DocumentChunk.builder()
                    .content(chunk.getText())
                    .embedding(embedding)
                    .document(document)
                    .build();
            chunkList.add(documentChunk);
        }

        documentChunkRepo.saveAll(chunkList);
        return document;
    }
}
