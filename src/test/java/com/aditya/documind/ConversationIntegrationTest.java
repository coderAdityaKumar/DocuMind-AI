package com.aditya.documind;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.containers.PostgreSQLContainer;

import com.aditya.documind.Entity.Conversation;
import com.aditya.documind.Repository.ConversationRepo;

import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ConversationIntegrationTest {
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16");

    static {
        postgres.start();
        System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgres.getUsername());
        System.setProperty("spring.datasource.password", postgres.getPassword());
    }

    @Autowired
    private ConversationRepo conversationRepository;

    @Test
    void shouldSaveConversation() {

        Conversation conv = Conversation.builder()
                .title("Integration Test")
                .build();

        Conversation saved = conversationRepository.save(conv);

        assertThat(saved.getId()).isNotNull();
    }
}
