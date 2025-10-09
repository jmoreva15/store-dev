CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(100)          NOT NULL,
    email      VARCHAR(150)          NOT NULL,
    password   VARCHAR(255)          NOT NULL,
    active     BIT(1)                NOT NULL,
    created_at datetime              NOT NULL,
    updated_at datetime              NULL,
    role_id    BIGINT                NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);
