drop table if exists users;

create table users (
	id bigint identity primary key,
	email varchar,
	password varchar
);