package com.aditya.documind.Embedding;

import java.util.List;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EmbeddingService {
    private final EmbeddingModel embeddingModel;

    public EmbeddingService(EmbeddingModel embeddingModel){
        this.embeddingModel=embeddingModel;
    }

    public float[] generateEmbedding(String text){
        EmbeddingRequest request=new EmbeddingRequest(List.of(text), null);
        EmbeddingResponse response=embeddingModel.call(request);
        return response.getResults().get(0).getOutput();
    }
}
