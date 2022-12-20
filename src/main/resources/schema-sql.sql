CREATE TABLE IF NOT EXISTS product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NULL,
    price FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS discount_card (
    number VARCHAR(255) NULL,
    percent SMALLSERIAL NOT NULL,
    PRIMARY KEY (number)
);

CREATE TABLE IF NOT EXISTS promotional_item (
    promotional_item_id SERIAL PRIMARY KEY
);

INSERT INTO promotional_item(
	promotional_item_id)
	VALUES (1), (3), (5), (7), (9);

INSERT INTO product(
	name, price)
	VALUES ('Box', 3.10),
	('Pen', 0.81),
	('Pencil', 0.52),
	('Keyboard', 8.10),
	('Table', 210.30),
	('Notebook', 5.51),
	('Book', 10.10),
	('Monitor', 120.22),
	('Car', 5000.70),
	('Christmas tree', 10.10),
	('Chair', 120.77),
	('Laptop', 700.50);

INSERT INTO discount_card(
	"number", percent)
	VALUES ('1234', 10),
	('1111', 11),
	('2222', 22),
	('5555', 5),
	('mycard', 2),
	('123456', 6),
	('9999', 9),
	('7777', 17);
