package com.aditya.documind.Config;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AIConfig {

    @Bean
    public ChatMemory chatMemory() {
        // Step 1: Create the repository (stores the messages)
        var chatMemoryRepository = new InMemoryChatMemoryRepository();
        
        // Step 2: Create the memory implementation with a window (last 10 messages)
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(10) // Keeps only the relevant context to save tokens
                .build();
    }
}