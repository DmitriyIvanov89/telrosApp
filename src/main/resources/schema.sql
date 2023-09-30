create table if not exists users (
    id bigserial,
    username varchar(255) not null unique,
    password varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    patronymic varchar(255),
    birth_date varchar(50) not null,
    email varchar(255) not null unique,
    phone varchar(255) not null,
    primary key (id)
)

create table if not exists roles(
    id bigserial,
    name varchar(255) not null unique,
    primary key (id)
)

create table if not exists users_roles (
    user_id bigserial not null,
    role_id bigserial not null,
    primary key (user_id,role_id),
    foreign key (user_id) references users(id),
    foreign key(role_id) references roles(id)
)