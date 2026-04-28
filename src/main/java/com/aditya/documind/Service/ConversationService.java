package com.aditya.documind.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aditya.documind.Entity.Conversation;
import com.aditya.documind.Entity.Organization;
import com.aditya.documind.Exception.ResourceNotFoundException;
import com.aditya.documind.Repository.ConversationRepo;
import com.aditya.documind.Repository.OrganizationRepo;
import com.aditya.documind.Tenant.TenantContext;

@Service
public class ConversationService{

    private final ConversationRepo conversationRepo;
    private final OrganizationRepo organizationRepo;

    public ConversationService(ConversationRepo conversationRepo,OrganizationRepo organizationRepo){
        this.conversationRepo=conversationRepo;
        this.organizationRepo=organizationRepo;
    }

    public Conversation createConversation(String title){
        Long tenantId=TenantContext.getTenantId();

        Organization org=organizationRepo.findById(tenantId).orElseThrow(()->new ResourceNotFoundException("Organization not found"));

        Conversation conversation=Conversation.builder()
        .title(title)
        .organization(org)
        .build();

        Conversation saveConversation=conversationRepo.save(conversation);

        return saveConversation;
    }

    public List<Conversation> getAllConversation(){
        Long tenantId=TenantContext.getTenantId();
        
        List<Conversation> conversations=conversationRepo.ffindAllConversationsByOrganizationId(tenantId);
        if(conversations.size()==0){
            throw new ResourceNotFoundException("No conversations found");
        }
        return conversations;
    }

    public void deleteConversation(Long id){
        conversationRepo.deleteById(id);
    }
}