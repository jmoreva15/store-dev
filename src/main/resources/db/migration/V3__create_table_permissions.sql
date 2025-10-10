CREATE TABLE permissions
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(100)          NOT NULL,
    active     BIT(1)                NOT NULL,
    deleted    BIT(1)                NOT NULL,
    created_at datetime              NOT NULL,
    updated_at datetime              NULL,
    CONSTRAINT pk_permissions PRIMARY KEY (id)
);
