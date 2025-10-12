CREATE TABLE tokens
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    value       VARCHAR(255)          NOT NULL,
    type        VARCHAR(255)          NOT NULL,
    user_id     BIGINT                NOT NULL,
    active      BIT(1)                NOT NULL,
    expiry_date DATETIME              NOT NULL,
    created_at  DATETIME              NOT NULL,
    updated_at  DATETIME              NULL,
    CONSTRAINT pk_tokens PRIMARY KEY (id)
);

ALTER TABLE tokens
    ADD CONSTRAINT uc_tokens_value UNIQUE (value);

ALTER TABLE tokens
    ADD CONSTRAINT FK_TOKENS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);
