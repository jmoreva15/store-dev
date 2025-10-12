CREATE TABLE stores
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    ruc        VARCHAR(100)          NOT NULL,
    address    VARCHAR(150)          NULL,
    phone      VARCHAR(50)           NULL,
    active     BIT(1)                NOT NULL,
    deleted    BIT(1)                NOT NULL,
    created_at datetime              NOT NULL,
    updated_at datetime              NULL,
    CONSTRAINT pk_stores PRIMARY KEY (id)
);
