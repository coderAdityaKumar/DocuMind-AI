package com.aditya.documind.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aditya.documind.Entity.Conversation;

@Repository
public interface ConversationRepo extends JpaRepository<Conversation,Long> {

    @Query(
        value = """
                select c from Conversation c where c.organization.id=:organizationId
                """
    )
    List<Conversation> ffindAllConversationsByOrganizationId(@Param("organizationId")Long id);
}
