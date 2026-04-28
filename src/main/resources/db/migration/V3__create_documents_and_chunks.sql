CREATE TABLE documents (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    file_type VARCHAR(50),
    organization_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_documents_org
    FOREIGN KEY (organization_id)
    REFERENCES organizations(id)
);

CREATE TABLE document_chunks (
    id BIGSERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    document_id BIGINT NOT NULL,

    CONSTRAINT fk_chunks_doc
    FOREIGN KEY (document_id)
    REFERENCES documents(id)
);