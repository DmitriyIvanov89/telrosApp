insert into users (username,password,first_name,last_name,patronymic,birth_date,email,phone)
values
('user','123','Cheshmi','Aslanova','Sabirovna','15.11.2000','cheshmi@mail.ru','+79501111111'),
('admin','123','Dmitriy','Ivanov','Alekseevich','24.04.1989','ivanov@mail.ru','+79111234567');

insert into roles (name)
values
('ROLE_USER'),
('ROLE_ADMIN');

insert into user_roles(user_id,role_id)
values
(1,1),
(2,2);