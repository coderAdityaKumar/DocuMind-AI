package com.aditya.documind.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aditya.documind.Embedding.EmbeddingService;
import com.aditya.documind.Entity.ChunkResult;
import com.aditya.documind.Entity.DocumentChunk;
import com.aditya.documind.Repository.DocumentChunkRepo;
import com.aditya.documind.Tenant.TenantContext;

@Service
public class RetrievalService {
    
    private final EmbeddingService embeddingService;
    private final DocumentChunkRepo documentChunkRepo;

    public RetrievalService (EmbeddingService embeddingService,DocumentChunkRepo documentChunkRepo){
        this.embeddingService=embeddingService;
        this.documentChunkRepo=documentChunkRepo;
    }

    public List<ChunkResult> retrieve(String question){
        String improvedQuestion="search_document: "+question;
        Long tenantId=TenantContext.getTenantId();

        float[] embedding=embeddingService.generateEmbedding(improvedQuestion);

        return documentChunkRepo.findTopKSimilar(embedding, tenantId, 3);
    }
}
