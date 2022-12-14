CREATE TABLE IF NOT EXISTS product (
    id bigint NOT NULL,
    name VARCHAR(255) NULL,
    price FLOAT NOT NULL,
    discount BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS discount_card (
    number VARCHAR(255) NULL,
    percent SMALLSERIAL NOT NULL,
    PRIMARY KEY (number)
);