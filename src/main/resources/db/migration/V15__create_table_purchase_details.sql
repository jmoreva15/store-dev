CREATE TABLE purchase_details
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    quantity         DECIMAL               NOT NULL,
    purchase_price   DECIMAL(10, 2)        NOT NULL,
    sale_price       DECIMAL(10, 2)        NOT NULL,
    store_product_id BIGINT                NULL,
    purchase_id      BIGINT                NULL,
    created_at       datetime              NOT NULL,
    updated_at       datetime              NULL,
    CONSTRAINT pk_purchase_details PRIMARY KEY (id)
);

ALTER TABLE purchase_details
    ADD CONSTRAINT FK_PURCHASE_DETAILS_ON_PURCHASE FOREIGN KEY (purchase_id) REFERENCES purchases (id);

ALTER TABLE purchase_details
    ADD CONSTRAINT FK_PURCHASE_DETAILS_ON_STORE_PRODUCT FOREIGN KEY (store_product_id) REFERENCES store_products (id);
