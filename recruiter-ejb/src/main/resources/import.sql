-- load seed data into the database using SQL statements
-- default password is "passWD123-"

insert into was_role (name) values ('ADMIN')
insert into was_role (name) values ('COMPANY')
insert into was_role (name) values ('USER')

insert into was_price (roleName, price) values ('ADMIN', 0)
insert into was_price (roleName, price) values ('COMPANY', 5)
insert into was_price (roleName, price) values ('USER', 1)

-- user: a@a.a
insert into was_address (title, name, street, streetNumber, zipCode, city) values ('Dr.', 'Master of Deaster (admin)', 'Street', '1', '12345', 'Berlin')
insert into was_user (email, password, passwordSalt, address_id) values ('a@a.a', '94fEC1wfzgGyqMmaoX+7SG7eOFKocPmY3US6b3uYdQ4=', '7MkZdGswgzvk1cDocG4v', (select id from was_address where city like 'Berlin'))
insert into was_user_role (user_id, role_id) values ((select id from was_user where email = 'a@a.a'), (select id from was_role where name = 'ADMIN'))

-- user: u@u.u
insert into was_address (name, street, streetNumber, zipCode, city, phone) values ('Homer Simpson', 'Street', '1', '12345', 'Springfield', '030 55555')
insert into was_user (email, password, passwordSalt, address_id) values ('u@u.u', '94fEC1wfzgGyqMmaoX+7SG7eOFKocPmY3US6b3uYdQ4=', '7MkZdGswgzvk1cDocG4v', (select id from was_address where city like 'Springfield'))
insert into was_user_role (user_id, role_id) values ((select id from was_user where email = 'u@u.u'), (select id from was_role where name = 'USER'))

-- user: c@c.c
insert into was_address (name, street, streetNumber, zipCode, city, phone) values ('Bill Gates', 'Microsoft Way', '1', '98052', 'Redmond', '+1 425-882-8080')
insert into was_user (email, password, passwordSalt, address_id) values ('c@c.c', '94fEC1wfzgGyqMmaoX+7SG7eOFKocPmY3US6b3uYdQ4=', '7MkZdGswgzvk1cDocG4v', (select id from was_address where city like 'Redmond'))
insert into was_user_role (user_id, role_id) values ((select id from was_user where email = 'c@c.c'), (select id from was_role where name = 'COMPANY'))

-- example article
insert into was_payBankCard (bic, iban) values ('BYLADEM6666', 'DE989008000009600305372')
insert into was_purchase (quantity, payBc_id, user_id) values (1, (select id from was_payBankCard where iban = 'DE989008000009600305372'), (select id from was_user where email = 'c@c.c'))
insert into was_article (purchase_id, title, content) values (1, 'We want You in our IT department', 'Our IT department is looking for a new software developer.')
insert into was_purchase (quantity, payBc_id, user_id) values (1, (select id from was_payBankCard where iban = 'DE989008000009600305372'), (select id from was_user where email = 'c@c.c'))
insert into was_article (purchase_id, title, content) values (2, 'We want You in our finance department', 'Our finance department is looking for a new assistant.')

insert into was_payBankCard (bic, iban) values ('BYLADEM1001', 'DE312008000009600309666')
insert into was_purchase (quantity, payBc_id, user_id) values (1, (select id from was_payBankCard where iban = 'DE31200800000960030966'), (select id from was_user where email = 'a@a.a'))
insert into was_article (purchase_id, title, content) values (3, 'very long long long long long long long long long long long long long long title', 'more...')
insert into was_comment (article_id, content) values ((select id from was_article where title = 'very long long long long long long long long long long long long long long title'), '...comment...')
insert into was_comment (article_id, content, user_id) values ((select id from was_article where title = 'very long long long long long long long long long long long long long long title'), 'foo bar', (select id from was_user where email = 'u@u.u'))

insert into was_payBankCard (bic, iban) values ('BYLADEM1001', 'DE31200800000960030900')
insert into was_purchase (quantity, payBc_id, user_id) values (1, (select id from was_payBankCard where iban = 'DE31200800000960030900'), (select id from was_user where email = 'u@u.u'))
insert into was_article (purchase_id, title, content) values (4, 'attack', 'should not possible from DB: &lt;p&gt;-&lt;/p&gt; <script type="text/javascript">alert("attack");</script> <br/> &#208; \u672c 本')
insert into was_comment (article_id, content) values ((select id from was_article where title = 'attack'), 'comment attack - should not possible from DB: <script type="text/javascript">alert("comment");</script>')

insert into was_payBankCard (bic, iban) values ('BYLADEM1001', 'DE89370400440532013000')
insert into was_purchase (quantity, payBc_id, user_id) values (1, (select id from was_payBankCard where iban = 'DE89370400440532013000'), (select id from was_user where email = 'u@u.u'))
insert into was_article (purchase_id, title, content) values (5, 'sample title', 'sample content')
insert into was_comment (article_id, content) values ((select id from was_article where title = 'sample title'), 'baz')
insert into was_comment (article_id, content, user_id) values ((select id from was_article where title = 'sample title'), 'foo bar', (select id from was_user where email = 'a@a.a'))
insert into was_comment (article_id, content) values ((select id from was_article where title = 'sample title'), 'qux')
insert into was_comment (article_id, content) values ((select id from was_article where title = 'sample title'), 'norf')

insert into was_payBankCard (bic, iban) values ('BYLADEM1001', 'DE89370400440532013010')
insert into was_purchase (quantity, payBc_id, user_id) values (1, (select id from was_payBankCard where iban = 'DE89370400440532013000'), (select id from was_user where email = 'u@u.u'))
insert into was_article (purchase_id, title, content) values (6, 'just for fun 1', '... but really')

insert into was_payBankCard (bic, iban) values ('BYLADEM1001', 'DE89370400440532013020')
insert into was_purchase (quantity, payBc_id, user_id) values (1, (select id from was_payBankCard where iban = 'DE89370400440532013000'), (select id from was_user where email = 'u@u.u'))
insert into was_article (purchase_id, title, content) values (7, 'just for fun 2', '... but really')
