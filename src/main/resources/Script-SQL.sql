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