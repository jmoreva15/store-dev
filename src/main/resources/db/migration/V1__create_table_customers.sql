CREATE TABLE customers
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    document_type   VARCHAR(50)           NOT NULL,
    document_number VARCHAR(50)           NOT NULL,
    name            VARCHAR(100)          NOT NULL,
    address         VARCHAR(150)          NULL,
    phone           VARCHAR(50)           NULL,
    deleted         BIT(1)                NOT NULL,
    created_at      datetime              NOT NULL,
    updated_at      datetime              NULL,
    CONSTRAINT pk_customers PRIMARY KEY (id)
);

ALTER TABLE customers
    ADD CONSTRAINT uc_customers_document_number UNIQUE (document_number);
