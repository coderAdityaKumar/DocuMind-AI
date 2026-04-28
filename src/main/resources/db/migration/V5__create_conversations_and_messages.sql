CREATE TABLE conversations (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    organization_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_conv_org
    FOREIGN KEY (organization_id)
    REFERENCES organizations(id)
);

CREATE TABLE messages (
    id BIGSERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    role VARCHAR(20) NOT NULL, -- USER or ASSISTANT
    conversation_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_msg_conv
    FOREIGN KEY (conversation_id)
    REFERENCES conversations(id)
);