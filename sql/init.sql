create table if not exists user_account
(
    id       bigserial
        constraint user_pk
            primary key,
    email    varchar(255) not null,
    password varchar(255) not null,
    username varchar(255)
);

alter table user_account
    owner to postgres;