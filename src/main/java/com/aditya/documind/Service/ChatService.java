package com.aditya.documind.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

import com.aditya.documind.Entity.ChunkResult;
import com.aditya.documind.Entity.Conversation;
import com.aditya.documind.Entity.Message;
import com.aditya.documind.Repository.ConversationRepo;
import com.aditya.documind.Repository.MessageRepo;

import io.micrometer.core.instrument.MeterRegistry;

@Service
public class ChatService {
        private final RetrievalService retrievalService;
        private final ChatClient chatClient;
        private final ConversationRepo conversationRepo;
        private final MessageRepo messageRepo;
        private final DocumentToolService documentToolService;
        private final MeterRegistry meterRegistry;

        public ChatService(RetrievalService retrievalService, ChatClient.Builder builder, ChatMemory chatMemory,
                        ConversationRepo conversationRepo, MessageRepo messageRepo,
                        DocumentToolService documentToolService,MeterRegistry meterRegistry) {
                this.retrievalService = retrievalService;
                var chatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory)
                                .conversationId("default") // This is a placeholder
                                .build();

                this.chatClient = builder
                                .defaultAdvisors(chatMemoryAdvisor)
                                .build();
                this.conversationRepo = conversationRepo;
                this.messageRepo = messageRepo;
                this.documentToolService = documentToolService;
                this.meterRegistry=meterRegistry;
        }

        public String ask(String question, Long conversationId) {
                Conversation conversation = conversationRepo.findById(conversationId)
                                .orElseThrow(() -> new RuntimeException("Conversation not found"));

                Message newMsg = Message.builder()
                                .content(question)
                                .role("USER")
                                .createdAt(LocalDateTime.now())
                                .conversation(conversation)
                                .build();

                messageRepo.save(newMsg);
                List<ChunkResult> results = retrievalService.retrieve(question);

                StringBuilder context = new StringBuilder();

                for (ChunkResult res : results) {
                        context.append(res.getContent()).append("\n\n");
                }

                String systemPrompt = """
                                You are an AI assistant with access to tools.

                                RULES:

                                1. If the question is about:
                                   - listing documents
                                   - file names
                                   - document metadata
                                   → USE tools

                                2. If the question is about:
                                   - document content
                                   - explanations
                                   → USE provided context

                                3. If answer is not found:
                                   → Say "I don't know"

                                CONTEXT:
                                %s

                                                """.formatted(context);

                Long start = System.currentTimeMillis();

                String response = chatClient.prompt()
                                .system(systemPrompt)
                                .user("""
                                                QUESTION : %s
                                                """.formatted(question))
                                .tools(documentToolService)
                                .call()
                                .content();
                Long duration=System.currentTimeMillis() - start;
                meterRegistry.timer("ai.response.time")
                .record(duration,TimeUnit.MILLISECONDS);

                meterRegistry.counter("ai.request.count")
                .increment();

                String cleanResponse = response.replaceAll("(?is)<think>.*?</think>", "").trim();

                Message aiMsg = Message.builder()
                                .content(cleanResponse)
                                .role("ASSISTANT")
                                .createdAt(LocalDateTime.now())
                                .conversation(conversation)
                                .build();

                messageRepo.save(aiMsg);

                return cleanResponse;
        }
}
