package com.aditya.documind.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aditya.documind.Common.ApiResponse;
import com.aditya.documind.Service.ChatService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService){
        this.chatService=chatService;
    }

    @GetMapping("/ask")
    public ApiResponse<String> search(@RequestParam String question,@RequestParam Long conversationId){
        String response=chatService.ask(question,conversationId);
        return new ApiResponse<>(true,"Success",response);
    }
}
