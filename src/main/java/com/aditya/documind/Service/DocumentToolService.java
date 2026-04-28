package com.aditya.documind.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import com.aditya.documind.Entity.Document;
import com.aditya.documind.Exception.ResourceNotFoundException;
import com.aditya.documind.Repository.DocumentRepo;
import com.aditya.documind.Tenant.TenantContext;

@Service
public class DocumentToolService {
    private final DocumentRepo documentRepo;

    public DocumentToolService(DocumentRepo documentRepo) {
        this.documentRepo = documentRepo;
    }

    @Tool(name = "list_documents", description = "Get all the documents uploaded by user")
    public String ListDocument() {
        Long tenantId = TenantContext.getTenantId();

        List<Document> docs = documentRepo.findAllByOrganizationId(tenantId);

        if (docs.size() == 0) {
            throw new ResourceNotFoundException("Document not found");
        }
        
        String response = docs.stream().map(doc -> "- " + doc.getName())
                .collect(Collectors.joining("\n"));

        System.out.println(response);
        return response;
    }

    @Tool(name = "count_documents",description = "Get total number of documents uploaded by user")
    public String countDocument(){
        Long tenantId=TenantContext.getTenantId();

        Long count=documentRepo.countDocumentsOfOrg(tenantId);

        System.out.println(count);

        return "Total Documents : "+count;
    }
}
