CREATE TABLE public.login
(
    id bigserial NOT NULL,
    email text NOT NULL,
    password varchar(1024) NOT NULL,
    creation_date date NOT NULL,
    status varchar(16) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
);

CREATE TABLE public.student
(
    id bigserial NOT NULL,
    id_login bigserial NOT NULL,
    firstname varchar(128) NOT NULL,
    lastname varchar(128) NOT NULL,
    active boolean NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY(id_login) REFERENCES public.login(id)
);

CREATE TABLE public.instructor
(
    id bigserial NOT NULL,
    id_login bigserial NOT NULL,
    firstname varchar (128) NOT NULL,
    lastname varchar (128) NOT NULL,
    employment_date date NOT NULL,
    dismissal_date date NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_login) REFERENCES public.login(id)
)

CREATE TABLE public.admin
(
    id bigserial NOT NULL,
    id_login bigserial NOT NULL,
    firstname varchar (128) NOT NULL,
    lastname varchar (128) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_login) REFERENCES public.login(id)
)

CREATE TABLE public.category
(
    id bigserial NOT NULL,
    category_name text NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (category_name)
);

CREATE TABLE public.course
(
    id bigserial NOT NULL,
    id_student bigserial NOT NULL,
    id_instructor bigserial NOT NULL,
    id_category bigserial NOT NULL,
    hours_done int NOT NULL,
    finished boolean NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_student) REFERENCES public.login(id),
    FOREIGN KEY (id_instructor) REFERENCES public.login(id),
    FOREIGN KEY (id_category) REFERENCES public.category(id)
);

CREATE TABLE public.instructor_category
(
    id bigserial NOT NULL,
    category_name text NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (category_name)
);

CREATE TABLE public.instructor_category
(
    id_instructor bigserial NOT NULL,
    id_category bigserial NOT NULL,

    PRIMARY KEY (id_instructor, id_category),
    FOREIGN KEY (id_instructor) REFERENCES public.instructor(id),
    FOREIGN KEY (id_category) REFERENCES public.category(id)
);

CREATE TABLE public.vehicle
(
    id bigserial NOT NULL,
    mark varchar (128) NOT NULL,
    model varchar (128) NOT NULL,
    id_category bigserial NOT NULL,
    plate_number varchar (128) NOT NULL,
    vin varchar (128) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_category) REFERENCES public.category(id)
);

CREATE TABLE public.instructor_vehicle
(
    id_instructor bigserial NOT NULL,
    id_vehicle bigserial NOT NULL,

    PRIMARY KEY (id_instructor, id_vehicle),
    FOREIGN KEY (id_instructor) REFERENCES public.instructor(id),
    FOREIGN KEY (id_vehicle) REFERENCES public.vehicle(id)
);

CREATE TABLE public.instructor_rating
(
    id bigserial NOT NULL,
    id_student bigserial NOT NULL,
    id_instructor bigserial NOT NULL,
    grade int NOT NULL,
    comment varchar (128) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_student) REFERENCES public.student(id),
    FOREIGN KEY (id_instructor) REFERENCES public.instructor(id)
);

CREATE TABLE public.event
(
    id bigserial NOT NULL,
    id_student bigserial NOT NULL,
    id_instructor bigserial NOT NULL,
    id_vehicle int,
    id_course varchar (128) NOT NULL,
    duration int NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_student) REFERENCES public.student(id),
    FOREIGN KEY (id_instructor) REFERENCES public.instructor(id),
    FOREIGN KEY (id_vehicle) REFERENCES public.vehicle(id),
    FOREIGN KEY (id_course) REFERENCES public.course(id)
)





