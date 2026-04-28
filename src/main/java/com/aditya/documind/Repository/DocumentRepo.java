package com.aditya.documind.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aditya.documind.Entity.Document;

@Repository
public interface DocumentRepo extends JpaRepository<Document,Long> {

    @Query(value = """
            select d from Document d where d.organization.id=:organizationId
            """)
    List<Document> findAllByOrganizationId(@Param("organizationId")Long id);

    @Query(
        value = """
                select count(d) from Document d where d.organization.id=:organizationId
                """
    )
    Long countDocumentsOfOrg(@Param("organizationId")Long id);
    
}
