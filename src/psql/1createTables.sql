CREATE TABLE public.login
(
    id bigserial NOT NULL,
    email text NOT NULL,
    password varchar(128) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
);

CREATE TABLE public.student
(
    id bigserial NOT NULL,
    id_login bigserial NOT NULL,
    firstname varchar(128) NOT NULL,
    lastname varchar(128) NOT NULL,
    creation_date date NOT NULL,
    active boolean NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY(id_login) REFERENCES public.login(id)
);


