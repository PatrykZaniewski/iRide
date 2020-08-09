CREATE TABLE public.login
(
    id bigserial NOT NULL,
    email text NOT NULL,
    password varchar(128) NOT NULL,
    creation_date date NOT NULL,
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
    PRIMARY KEY (id),
    FOREIGN KEY (id_student) REFERENCES public.login(id),
    FOREIGN KEY (id_instructor) REFERENCES public.login(id),
    FOREIGN KEY (id_category) REFERENCES public.category(id)
);

CREATE TABLE public.category
(
    id bigserial NOT NULL,
    category_name text NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (category_name)
);

