INSERT INTO user_(email, password, creation_date, status, account_role)
values ('a@iride.pl', '123123', '2019-03-05', 'ACTIVE', 'STUDENT'),
       ('b@iride.pl', 'aaaaa', '2019-01-05', 'ACTIVE', 'STUDENT'),
       ('c@iride.pl', 'qweqwe', '2016-05-05', 'ACTIVE', 'STUDENT'),
       ('d@iride.pl', 'aqaqaq', '2018-05-05', 'ACTIVE', 'INSTRUCTOR'),
       ('e@iride.pl', 'qwerty', '2019-02-02', 'ACTIVE', 'INSTRUCTOR'),
       ('f@iride.pl', 'zzzzzz', '2019-12-01', 'ACTIVE', 'ADMIN');

INSERT INTO student(id_user, firstname, lastname, phone_number)
values (1, 'Jan', 'Kowalski', '694213769'),
       (2, 'Adam', 'Konieczny', '501487748'),
       (3, 'Joanna', 'Szybka', '503665482');


INSERT INTO instructor(id_user, firstname, lastname, employment_date, phone_number)
values (4, 'Monika', 'Słoneczna', '2016-01-01', '554857689'),
       (5, 'Paweł', 'Andrut', '2019-03-01', '684584889');

INSERT INTO admin(id_user, firstname, lastname, phone_number)
values (6, 'Tadeusz', 'Mysz', '556445778');

INSERT INTO category(category_name, category_type)
values ('AM', 'THEORY'),
       ('A1', 'THEORY'),
       ('A2', 'THEORY'),
       ('A', 'THEORY'),
       ('B1', 'THEORY'),
       ('B', 'THEORY'),
       ('B+E', 'THEORY'),
       ('C', 'THEORY'),
       ('C+E', 'THEORY'),
       ('C1', 'THEORY'),
       ('C1+E', 'THEORY'),
       ('D', 'THEORY'),
       ('D+E', 'THEORY'),
       ('D1+E', 'THEORY'),
       ('T', 'THEORY'),
       ('AM', 'PRACTICE'),
       ('A1', 'PRACTICE'),
       ('A2', 'PRACTICE'),
       ('A', 'PRACTICE'),
       ('B1', 'PRACTICE'),
       ('B', 'PRACTICE'),
       ('B+E', 'PRACTICE'),
       ('C', 'PRACTICE'),
       ('C+E', 'PRACTICE'),
       ('C1', 'PRACTICE'),
       ('C1+E', 'PRACTICE'),
       ('D', 'PRACTICE'),
       ('D+E', 'PRACTICE'),
       ('C1+E', 'PRACTICE'),
       ('T', 'PRACTICE');

INSERT INTO vehicle(mark, model, id_category, plate_number, vin)
values ('Toyota', 'Yaris', 22, 'WZ351AW', 'JS1811116486A'),
        ('Mercedes', 'Tir', 26, 'WB223AQ', 'JS12313131232'),
        ('Renault', 'Autokar', 29, 'WWZ233', 'JS13123123123');

INSERT INTO instructor_category(id_instructor, id_category)
values (1, 1),
        (1, 2),
        (1, 3),
        (1, 4),
        (1, 5),
        (1, 6),
        (1, 7),
        (1, 8),
        (1, 9),
        (1, 10),
        (1, 11),
        (1, 12),
        (1, 13),
        (1, 14),
        (1, 15),
        (1, 16),
        (1, 17),
        (1, 18),
        (1, 19),
        (1, 20),
        (1, 21),
        (1, 22),
        (2, 21),
        (2, 22),
        (2, 23),
        (2, 24),
        (2, 25),
        (2, 26),
        (2, 27),
        (2, 28),
        (2, 29),
        (2, 30);

insert into instructor_vehicle(id_instructor, id_vehicle)
values (1, 1),
        (2, 1),
        (2, 2),
        (2, 3);

-- insert into instructor_rating(id_student, id_instructor, grade, comment)
-- values (1, 1, 5, 'OK');

insert into course(id_student, id_instructor, id_category, hours_minimum, hours_remaining, hours_done, status)
values (1, 1, 6, 30, 30, 0, 'IN_PROGRESS'),
         (1, 2, 22, 30, 30, 0, 'WAITING'),
         (2, 1, 6, 30, 0, 30, 'FINISHED'),
         (2, 2, 22, 30, 8, 28, 'IN_PROGRESS'),
         (3, 1, 6, 30, 0, 30, 'FINISHED'),
         (3, 2, 22, 30, 17, 13, 'WAITING');

insert into event(id_student, id_instructor, id_vehicle, id_course, duration, comment)
values (1, 1, NULL, 1, 15, NULL),
       (1, 1, NULL, 1, 15, NULL),
       (2, 1, NULL, 3, 15, NULL),
       (2, 1, NULL, 3, 15, NULL),
       (2, 2, 1, 4, 4, NULL),
       (2, 2, 1, 4, 4, NULL),
       (3, 1, NULL, 5, 15, NULL),
       (3, 1, NULL, 5, 15, NULL),
       (3, 2, 1, 6, 10, NULL),
       (3, 2, 1, 6, 10, NULL);








-- INSERT INTO course(id_student, id_instructor, id_category, hours_minimum, hours_remaining, hours_done, status)
-- values (1, )

