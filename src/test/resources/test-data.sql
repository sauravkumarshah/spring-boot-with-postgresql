drop table if exists employee_tbl;

create table employee_tbl(
	empid int GENERATED ALWAYS AS IDENTITY primary key,
	ename varchar(50),
	address varchar(20)
);

drop table if exists user_info_tbl;

create table user_info_tbl(
	id int GENERATED ALWAYS AS IDENTITY primary key,
	name varchar(50),
	email varchar(50),
	password varchar(500),
	roles varchar(50)
);
