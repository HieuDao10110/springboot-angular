create database accountsmanager;
use accountsmanager;
create table user (
	id int not null auto_increment, 
    username varchar(255),
    password varchar(255),
    role int not null,
    primary key(id)
);
insert into user(username, password, role)
values('admin','admin',1);
insert into user(username, password, role)
values('user','user',0);