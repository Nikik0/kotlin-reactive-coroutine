create table company(
    id serial primary key,
    name varchar(100) not null,
    address varchar(200)
);
create table user(
    id serial primary key,
    name varchar(100),
    email varchar(100),
    age int,
    company_id bigint
);