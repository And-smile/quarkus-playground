create table orders
(
    id uuid not null
        constraint orders_pkey
            primary key,
    customer_name varchar(255),
    ordered_at TIMESTAMP
);
create table menu_items
(
    id uuid not null
        constraint menu_items_pkey
            primary key,
    name varchar(255),
    price numeric(19,2)
);

create table order_items
(
    id uuid not null
        constraint order_items_pkey
            primary key,
    name varchar(255),
    price numeric(19,2),
    menu_item_id uuid
        constraint order_items_menu_item_id_fk
            references menu_items,
    order_id  uuid
        constraint order_items_order_id_fk
            references orders
);
