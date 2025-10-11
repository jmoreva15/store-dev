CREATE TABLE products
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(100)          NOT NULL,
    description   VARCHAR(255)          NULL,
    active        BIT(1)                NOT NULL,
    deleted       BIT(1)                NOT NULL,
    created_at    datetime              NOT NULL,
    updated_at    datetime              NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);
