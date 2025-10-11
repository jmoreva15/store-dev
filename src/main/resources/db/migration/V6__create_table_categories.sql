CREATE TABLE categories
(
    id            BIGINT AUTO_INCREMENT    NOT NULL,
    name          VARCHAR(100)             NOT NULL,
    description   VARCHAR(255)             NULL,
    deleted       BIT(1)                   NOT NULL,
    created_at    datetime                 NOT NULL,
    updated_at    datetime                 NULL,
    CONSTRAINT    pk_categories PRIMARY    KEY (id)
);
