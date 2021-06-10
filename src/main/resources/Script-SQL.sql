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

-- create table imsos.cart(
-- 	cart_id int primary key auto_increment,
--     account_id int not null
-- );

create table imsos.order_item(
	order_item_id int primary key auto_increment,
    quantity int not null default 1,
    amount double not null,
    purchased_at datetime default current_timestamp,
    product_id int not null
);

create table imsos.order(
	order_id int primary key auto_increment,
    account_id int not null,
    payment_id int not null,
    customer_address_id int not null,
    order_status varchar(15) not null,
    total_amount double not null,
    delivered_at datetime default current_timestamp on update current_timestamp,
    ordered_at datetime default current_timestamp
);

create table imsos.customer_address_info(
	customer_address_id int primary key auto_increment,
    account_id int not null,
    first_name varchar(15) not null,
	last_name varchar(15) not null,
    phone_number int(13) not null,
    postal_code int(5) not null,
    region varchar(15) not null,
    city varchar(20) not null,
    province varchar(20) not null,
    barangay varchar(30) not null,
    street varchar(30) not null
);
create table imsos.payment_method (
	payment_id int primary key auto_increment,
    payment_method varchar(10) not null
);

-- create table imsos.cart_item(
-- 	cart_item_id int primary key auto_increment,
--     quantity int not null default 1,
--     amount double not null,
--     added_at datetime default current_timestamp,
--     product_id int not null,
--     cart_id int not null
-- );




-- insert into imsos.account (first_name, last_name) values ("jerome","viray");

-- update imsos.account set last_name = "seccion" where account_id = 1;