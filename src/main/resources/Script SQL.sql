create table imsos.product (
	product_id int(7) primary key auto_increment,
    product_name varchar(20) not null,
    product_descritions text(255) not null,
    product_price double not null,
    product_deleted tinyint not null default 0,
    created_at datetime not null default current_timestamp,
    updated_at datetime not null default current_timestamp on update current_timestamp
);