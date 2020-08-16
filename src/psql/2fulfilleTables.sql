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