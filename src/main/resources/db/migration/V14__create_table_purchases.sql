CREATE TABLE purchases
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    purchase_price  DECIMAL(10, 2)        NOT NULL,
    sale_price      DECIMAL(10, 2)        NOT NULL,
    supplier_id     BIGINT                NULL,
    store_id        BIGINT                NULL,
    created_at      datetime              NOT NULL,
    updated_at      datetime              NULL,
    CONSTRAINT pk_purchases PRIMARY KEY (id)
);

ALTER TABLE purchases
    ADD CONSTRAINT FK_PURCHASES_ON_STORE FOREIGN KEY (store_id) REFERENCES stores (id);

ALTER TABLE purchases
    ADD CONSTRAINT FK_PURCHASES_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES suppliers (id);
