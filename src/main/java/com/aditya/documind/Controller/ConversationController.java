package com.aditya.documind.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aditya.documind.Common.ApiResponse;
import com.aditya.documind.DTO.ConversationRequest;
import com.aditya.documind.DTO.ConversationResponse;
import com.aditya.documind.Entity.Conversation;
import com.aditya.documind.Service.ConversationService;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService){
        this.conversationService=conversationService;
    }
    
    @PostMapping
    public ApiResponse<ConversationResponse> create(@RequestBody ConversationRequest request){
        Conversation conversation=conversationService.createConversation(request.title());
        return new ApiResponse<>(true,"Created",new ConversationResponse(conversation.getId(),conversation.getTitle()));
    }

    @GetMapping
    public ApiResponse<List<ConversationResponse>> getAll(){
        List<Conversation> conversations=conversationService.getAllConversation();
        
        List<ConversationResponse> responses= conversations.stream().map(conversation->
            new ConversationResponse(conversation.getId(),conversation.getTitle())
        ).collect(Collectors.toList());

        return new ApiResponse<>(true,"Success",responses);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> delete(@PathVariable Long id){
        conversationService.deleteConversation(id);
        return new ApiResponse<>(true,"deleted","Conversation deleted successfully");
    }
}
