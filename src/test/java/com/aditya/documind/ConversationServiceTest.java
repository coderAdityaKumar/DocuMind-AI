package com.aditya.documind;

import org.junit.jupiter.api.*;
import org.mockito.*;

import com.aditya.documind.Entity.Conversation;
import com.aditya.documind.Entity.Organization;
import com.aditya.documind.Repository.ConversationRepo;
import com.aditya.documind.Repository.OrganizationRepo;
import com.aditya.documind.Service.ConversationService;
import com.aditya.documind.Tenant.TenantContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ConversationServiceTest {

    @Mock
    private ConversationRepo conversationRepository;

    @Mock
    private OrganizationRepo organizationRepository;

    @InjectMocks
    private ConversationService conversationService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        TenantContext.setTenantId(1L);
    }

    @AfterEach
    void cleanup() {
        TenantContext.clear();
    }

    @Test
    void shouldCreateConversation() {

        Organization org = Organization.builder().id(1L).name("Test Org").build();

        when(organizationRepository.findById(1L)).thenReturn(Optional.of(org));

        when(conversationRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Conversation conv = conversationService.createConversation("Test Chat");

        assertThat(conv.getTitle()).isEqualTo("Test Chat");

        verify(conversationRepository).save(any());
    }
}
