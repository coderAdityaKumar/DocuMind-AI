package com.aditya.documind.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aditya.documind.Entity.ChunkResult;
import com.aditya.documind.Entity.DocumentChunk;

@Repository
public interface DocumentChunkRepo extends JpaRepository<DocumentChunk, Long> {

        @Query(value = """
                        SELECT
                            dc.id as id,
                            dc.content as content,
                            (1 - (dc.embedding <=> CAST(:embedding AS vector))) as similarity
                        FROM document_chunks dc
                        JOIN documents d ON dc.document_id = d.id
                        WHERE d.organization_id = :tenantId
                        AND (1 - (dc.embedding <=> CAST(:embedding AS vector))) > 0.3
                        ORDER BY similarity DESC
                        LIMIT :limit
                        """, nativeQuery = true)
        List<ChunkResult> findTopKSimilar(
                        @Param("embedding") float[] embedding,
                        @Param("tenantId") Long tenantId,
                        @Param("limit") int limit);

}
