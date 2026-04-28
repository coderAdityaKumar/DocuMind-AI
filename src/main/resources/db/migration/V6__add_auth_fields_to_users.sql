alter table users
add column password varchar(255),
add column role varchar(50) default 'user';