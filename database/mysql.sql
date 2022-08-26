create database accountsmanager;
use accountsmanager;
create table user (
	id int not null auto_increment, 
    username varchar(32),
    password varchar(32),
    salt varchar(32) not null,
    role int not null,
    primary key(id)
);
 # password is 'admin'
insert into user(username, password, salt, role)
values('admin','50e5864148013fc203fe10856fc7f3cd', 'a62fa1e591b7bba1c20b0f7091e62794', 1);
 # password is 'user'
insert into user(username, password, salt, role)
values('user','5caa27e4a2d39ee5d545ea9ae66fee4a', 'e7b0d84dfa83b0a96f236a1565627c40',0);