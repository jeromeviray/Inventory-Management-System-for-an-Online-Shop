-- create table imsos.product (
-- 	product_id int(7) primary key auto_increment,
--     product_name varchar(20) not null,
--     product_descriptions text not null,
--     product_price double not null,
--     created_at datetime not null default current_timestamp,
--     updated_at datetime not null default current_timestamp on update current_timestamp,
--     product_is_deleted tinyint(1) not null default 0
-- );


-- create table imsos.inventory(
-- 	inventory_id int(7) auto_increment primary key,
--     stock int(7) not null default 1,
--     product_id int(7) not null
-- );

-- create table imsos.account(
-- 	account_id int primary key auto_increment,
--     first_name varchar(15) not null,
--     last_name varchar(15) not null,
--     created_at datetime not null default current_timestamp,
--     updated_at datetime not null default current_timestamp on update current_timestamp,
--     is_deleted tinyint not null default 0
-- );

create table imsos.cart(
	cart_id int primary key auto_increment,
    account_id int not null
);

create table imsos.order_item(
	order_item_id int primary key auto_increment,
    quantity int not null default 1,
    total_amount double not null,
    created_at datetime default current_timestamp,
    updated_at datetime default current_timestamp on update current_timestamp,
    is_removed tinyint not null default 0,
    product_id int not null
);

create table imsos.cart_item(
	cart_item_id int primary key auto_increment,
    quantity int not null default 1,
    amount double not null,
    added_at datetime default current_timestamp,
    product_id int not null,
    cart_id int not null
);




-- insert into imsos.account (first_name, last_name) values ("jerome","viray");

-- update imsos.account set last_name = "seccion" where account_id = 1;