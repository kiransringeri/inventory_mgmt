create database inventory_mgmt;
GRANT ALL PRIVILEGES ON inventory_mgmt.* TO 'weavedin'@'localhost' IDENTIFIED BY 'weavedin';

use inventory_mgmt;

create table store(
	id integer UNSIGNED primary key auto_increment,
	name varchar(100) not null
);
create table branch(
	id integer UNSIGNED primary key auto_increment,
	storeId integer UNSIGNED not null,
	CONSTRAINT fk_branch_store FOREIGN KEY (storeId) REFERENCES store(id)
);
create table user(
	id integer UNSIGNED primary key auto_increment,
	name varchar(100) not null,
	storeId integer UNSIGNED not null,
	CONSTRAINT fk_user_store FOREIGN KEY (storeId) REFERENCES store(id)
);
create table item(
	id integer UNSIGNED primary key auto_increment,
	branchId integer UNSIGNED not null,
	name varchar(100) not null,
	brand varchar(100) not null,
	category varchar(100) not null,
	productCode varchar(100) not null,
	CONSTRAINT fk_item_branch FOREIGN KEY (branchId) REFERENCES branch(id)
);
create table variant(
	id integer UNSIGNED primary key auto_increment,
	itemId integer UNSIGNED not null,
	name varchar(100) not null,
	sellingPrice decimal(8,2) unsigned not null,
	costPrice decimal(8,2) unsigned not null,
	quantity SMALLINT unsigned not null,
	CONSTRAINT fk_variant_item FOREIGN KEY (itemId) REFERENCES item(id)
);
create table variant_property(
	id integer UNSIGNED primary key auto_increment,
	variantId integer UNSIGNED not null,
	property varchar(100) not null,
	value varchar(100)not null,
	CONSTRAINT fk_variant_property_variant FOREIGN KEY (variantId) REFERENCES variant(id)
);

create table user_action(
	id bigint UNSIGNED primary key auto_increment,
	userId integer unsigned not null,
	branchId integer unsigned not null,
	actionTime datetime not null, 
	action varchar(100) not null,
	property text not null,
	itemName varchar(100) not null,
	CONSTRAINT fk_user_action_user FOREIGN KEY (userId) REFERENCES user(id),
	constraint fk_user_action_branch foreign key (branchId) references branch (id)
);

alter table item add constraint unik_name unique (branchId, name);
alter table variant add constraint unik_name unique (itemId, name);
