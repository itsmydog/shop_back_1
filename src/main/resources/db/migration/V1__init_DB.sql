DROP SEQUENCE IF EXISTS bucket_seq;
create sequence bucket_seq start with 1 increment by 1;
CREATE TABLE buckets
(
    id      bigint not null,
    user_id bigint UNIQUE,
    primary key (id)
);

create table buckets_products
(
    bucket_id  bigint not null,
    product_id bigint not null
);

DROP SEQUENCE IF EXISTS category_seq;
create sequence category_seq start with 1 increment by 1;
create table categories
(
    id    bigint not null,
    title varchar(255),
    primary key (id)
);


DROP SEQUENCE IF EXISTS order_details_seq;
create sequence order_details_seq start with 1 increment by 1;
create table order_details
(
    amount     numeric(38, 2),
    price      numeric(38, 2),
    id         bigint not null,
    order_id   bigint,
    product_id bigint,
    primary key (id)
);

DROP SEQUENCE IF EXISTS order_seq;
create sequence order_seq start with 1 increment by 1;
create table orders
(
    sum     numeric(38, 2),
    created timestamp(6),
    id      bigint not null,
    updated timestamp(6),
    user_id bigint,
    address varchar(255),
    status  varchar(255) check (status in ('NEW', 'APPROVE', 'CANCELED', 'PAID', 'CLOSED')),
    primary key (id)
);


create table orders_order_details
(
    order_details_id bigint not null unique,
    order_id          bigint not null
);

DROP SEQUENCE IF EXISTS product_seq;
create sequence product_seq start with 1 increment by 1;
create table products
(
    price numeric(38, 2),
    id    bigint not null,
    title varchar(255),
    primary key (id)
);
create table products_categories
(
    category_id bigint not null,
    product_id  bigint not null
);

DROP SEQUENCE IF EXISTS users_seq;
create sequence users_seq start with 1 increment by 1;
create table users
(
    archive   boolean,
    id        bigint not null,
    email     varchar(255),
    name      varchar(255),
    password  varchar(255),
    role      varchar(255),
    primary key (id)
);

ALTER TABLE IF EXISTS buckets
    add constraint buckets_fk_user
        foreign key (user_id)
            references users;

ALTER TABLE IF EXISTS buckets_products
    add constraint buckets_products_fk_products
        foreign key (product_id)
            references products;

ALTER TABLE IF EXISTS buckets_products
    add constraint buckets_products_fk_buckets
        foreign key (bucket_id)
            references buckets;

ALTER TABLE IF EXISTS order_details
    add constraint order_details_fk_orders
        foreign key (order_id)
            references orders;

ALTER TABLE IF EXISTS order_details
    add constraint order_details_fk_products
        foreign key (product_id)
            references products;

ALTER TABLE IF EXISTS orders
    add constraint orders_fk_users
        foreign key (user_id)
            references users;

ALTER TABLE IF EXISTS orders_order_details
    add constraint orders_order_details_fk_order_details
        foreign key (order_details_id)
            references order_details;

ALTER TABLE IF EXISTS orders_order_details
    add constraint orders_order_details_fk_orders
        foreign key (order_id)
            references orders;

ALTER TABLE IF EXISTS products_categories
    add constraint products_categories_fk_categories
        foreign key (category_id)
            references categories;

ALTER TABLE IF EXISTS products_categories
    add constraint products_categories_fk_products
        foreign key (product_id)
            references products;

