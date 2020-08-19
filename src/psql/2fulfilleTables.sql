INSERT INTO login(email, password, creation_date, status)
values ('a@iride.pl', '123123', '2019-03-05', 'ACTIVE'),
       ('b@iride.pl', 'aaaaa', '2019-01-05', 'ACTIVE'),
       ('c@iride.pl', 'qweqwe', '2016-05-05', 'ACTIVE'),
       ('d@iride.pl', 'aqaqaq', '2018-05-05', 'ACTIVE'),
       ('e@iride.pl', 'qwerty', '2019-02-02', 'ACTIVE'),
       ('f@iride.pl', 'zzzzzz', '2019-12-01', 'ACTIVE');

INSERT INTO student(id_login, firstname, lastname, phone_number)
values (1, 'Jan', 'Kowalski', '694213769'),
       (2, 'Adam', 'Konieczny', '501487748'),
       (3, 'Joanna', 'Szybka', '503665482');


INSERT INTO instructor(id_login, firstname, lastname, employment_date, phone_number)
values (4, 'Monika', 'Słoneczna', '2016-01-01', '554857689'),
       (5, 'Paweł', 'Andrut', '2019-03-01', '684584889');

INSERT INTO admin(id_login, firstname, lastname, employment_date, phone_number)
values (6, 'Tadeusz', 'Mysz', '2012-01-01', '556445778');

INSERT INTO category(category_name, category_type)
values ('AM', 'Teoria'),
       ('A!', 'Teoria'),
       ('A2', 'Teoria'),
       ('A', 'Teoria'),
       ('B1', 'Teoria'),
       ('B', 'Teoria'),
       ('B+E', 'Teoria'),
       ('C', 'Teoria'),
       ('C+E', 'Teoria'),
       ('C1', 'Teoria'),
       ('C1+E', 'Teoria'),
       ('D', 'Teoria'),
       ('D+E', 'Teoria'),
       ('C1+E', 'Teoria'),
       ('D1+E', 'Teoria'),
       ('T', 'Teoria')

       --Jeszcze kwalifikacje wstepne