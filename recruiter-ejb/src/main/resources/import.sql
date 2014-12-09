-- load seed data into the database using SQL statements

insert into was_role(name) values ('ADMIN')
insert into was_role(name) values ('COMPANY')
insert into was_role(name) values ('USER')

insert into was_address(title, name, street, streetNumber, zipCode, city) values ('Dr.', 'Homer Simpson', 'Street', '1', '12345', 'Springfield')
insert into was_user(email, password, passwordSalt, activationKey, signinAttempts, address_id) values ('l@l.l', 'fsbv1UooTJ3hPChjo5HgY9T5caG6TG4xY7Qb10k2T8E=', '7MkZdGswgzvk1cDocG4v', 'activationkey', 0, (select id from was_address where city like 'Springfield'))
insert into was_user_role(user_id, role_id) values ((select id from was_user where email = 'l@l.l'), (select id from was_role where name = 'USER'))
