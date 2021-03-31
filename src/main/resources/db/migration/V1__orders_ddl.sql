create table orders
(
    id                 uuid not null,
    customer_full_name varchar(255),
    order_item_name    varchar(255),
    total_price        decimal(19, 4),
    status             varchar(255),
    primary key (id)
)