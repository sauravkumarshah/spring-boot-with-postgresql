drop table if exists employee;

create table employee(
	empid int GENERATED ALWAYS AS IDENTITY primary key,
	ename varchar(50),
	address varchar(20)
);
