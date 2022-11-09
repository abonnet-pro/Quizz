create table if not exists user_account
(
    id       bigserial
        constraint user_pk
            primary key,
    created timestamp,
    updated timestamp,

    email    varchar(255) not null unique ,
    password varchar(255) not null,
    username varchar(255) unique,
    role     varchar(255) NOT NULL
);

alter table user_account
    owner to postgres;

create table if not exists categorie
(
    id       bigserial
        constraint categorie_pk
            primary key,
    created timestamp,
    updated timestamp,

    name varchar(255) NOT NULL
);

alter table categorie
    owner to postgres;

create table if not exists question
(
    id       bigserial
        constraint question_pk
            primary key,
    created timestamp,
    updated timestamp,

    description varchar(255) NOT NULL,
    categorie_id bigint,
    reponse_1 varchar(255) NOT NULL,
    reponse_2 varchar(255) NOT NULL,
    reponse_3 varchar(255) NOT NULL,
    reponse_4 varchar(255) NOT NULL,
    constraint fk_categorie_qestion
        foreign key (categorie_id)
            references categorie(id)
);

alter table question
    owner to postgres;