CREATE TABLE public.user_
(
    id            serial     NOT NULL,
    email         text          NOT NULL,
    password      varchar(1024) NOT NULL,
    creation_date date          NOT NULL,
    status        varchar(128)   NOT NULL,
    account_role varchar(16)   NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
);

CREATE TABLE public.student
(
    id           serial    NOT NULL,
    id_user     integer    NOT NULL,
    firstname    varchar(128) NOT NULL,
    lastname     varchar(128) NOT NULL,
    phone_number varchar(16)  NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_user) REFERENCES public.user_ (id)
);

CREATE TABLE public.instructor
(
    id              serial    NOT NULL,
    id_user        integer    NOT NULL,
    firstname       varchar(128) NOT NULL,
    lastname        varchar(128) NOT NULL,
    employment_date date,
    dismissal_date  date,
    phone_number    varchar(16)  NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_user) REFERENCES public.user_ (id)
);

CREATE TABLE public.admin
(
    id           serial    NOT NULL,
    id_user     integer    NOT NULL,
    firstname    varchar(128) NOT NULL,
    lastname     varchar(128) NOT NULL,
    phone_number varchar(16)  NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_user) REFERENCES public.user_ (id)
);

CREATE TABLE public.category
(
    id            serial NOT NULL,
    category_name text      NOT NULL,
    category_type text      NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE public.instructor_category
(
    id           serial    NOT NULL,
    id_instructor integer NOT NULL,
    id_category   integer NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (id_instructor) REFERENCES public.instructor (id),
    FOREIGN KEY (id_category) REFERENCES public.category (id)
);

CREATE TABLE public.vehicle
(
    id           serial    NOT NULL,
    mark         varchar(128) NOT NULL,
    model        varchar(128) NOT NULL,
    id_category  integer,
    plate_number varchar(128) NOT NULL,
    vin          varchar(128) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_category) REFERENCES public.category (id)
);

CREATE TABLE public.instructor_vehicle
(
    id            serial NOT NULL,
    id_instructor integer NOT NULL,
    id_vehicle    integer NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (id_instructor) REFERENCES public.instructor (id),
    FOREIGN KEY (id_vehicle) REFERENCES public.vehicle (id)
);

CREATE TABLE public.course
(
    id              serial   NOT NULL,
    id_student      integer   NOT NULL,
    id_instructor   integer   NOT NULL,
    id_category     integer   NOT NULL,
    hours_minimum   int         NOT NULL,
    hours_remaining int         NOT NULL,
    hours_done      int         NOT NULL,
    status          varchar(16) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_student) REFERENCES public.student (id),
    FOREIGN KEY (id_instructor) REFERENCES public.instructor (id),
    FOREIGN KEY (id_category) REFERENCES public.category (id)
);

CREATE TABLE public.instructor_rating
(
    id            serial    NOT NULL,
    id_student    integer    NOT NULL,
    id_course     integer    NOT NULL,
    id_instructor integer    NOT NULL,
    grade         int          NOT NULL,
    comment       varchar(128) NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (id_student) REFERENCES public.student (id),
    FOREIGN KEY (id_course) REFERENCES public.course (id),
    FOREIGN KEY (id_instructor) REFERENCES public.instructor (id)
);

CREATE TABLE public.event
(
    id            serial NOT NULL,
    id_student    integer NOT NULL,
    id_instructor integer NOT NULL,
    id_vehicle    integer,
    id_course     integer NOT NULL,
    start_date    timestamp NOT NULL,
    end_date      timestamp NOT NULL,
    duration      int       NOT NULL,
    comment       varchar(256),
    PRIMARY KEY (id),
    FOREIGN KEY (id_student) REFERENCES public.student (id),
    FOREIGN KEY (id_instructor) REFERENCES public.instructor (id),
    FOREIGN KEY (id_vehicle) REFERENCES public.vehicle (id),
    FOREIGN KEY (id_course) REFERENCES public.course (id)
);





