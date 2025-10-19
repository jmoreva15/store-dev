CREATE TABLE store_products
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    stock          DECIMAL               NOT NULL,
    purchase_price DECIMAL(10, 2)        NOT NULL,
    sale_price     DECIMAL(10, 2)        NOT NULL,
    product_id     BIGINT                NULL,
    store_id       BIGINT                NULL,
    created_at     datetime              NOT NULL,
    updated_at     datetime              NULL,
    CONSTRAINT pk_store_products PRIMARY KEY (id)
);

ALTER TABLE store_products
    ADD CONSTRAINT FK_STORE_PRODUCTS_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE store_products
    ADD CONSTRAINT FK_STORE_PRODUCTS_ON_STORE FOREIGN KEY (store_id) REFERENCES stores (id);
