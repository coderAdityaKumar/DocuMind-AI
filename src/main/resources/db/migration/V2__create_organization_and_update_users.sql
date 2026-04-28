CREATE TABLE organizations (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Add tenant_id to users table
ALTER TABLE users
ADD COLUMN organization_id BIGINT;

-- Foreign key constraint
ALTER TABLE users
ADD CONSTRAINT fk_users_org
FOREIGN KEY (organization_id)
REFERENCES organizations(id);