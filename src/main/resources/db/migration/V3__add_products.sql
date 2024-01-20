INSERT INTO products (id, price, title)
VALUES
    (1, 10, 'Cheese'),
    (2, 12, 'Moloko'),
    (3, 15, 'Hleb'),
    (4, 5, 'egg'),
    (5, 48, 'Pasta');

ALTER SEQUENCE product_seq RESTART WITH 6;
