CREATE TABLE USER
(
    id        Long PRIMARY KEY Auto_increment,
    email varchar(255),
    password  varchar(255),
    password_reset_key varchar(255)
);

INSERT INTO USER(id, email, password)
VALUES (1, 'admin@byom.de', '{noop}admin'),
       (2, 'Marcin@byom.de', '{noop}asdf'),
       (3, 'Karolinka@byom.de', '{noop}admin');

CREATE TABLE USER_ROLE
(
    id      Long PRIMARY KEY Auto_increment,
    user_id Long,
    role    varchar(255)
);

INSERT INTO USER_ROLE (id, user_id, role)
VALUES (1, 1, 'ROLE_ADMIN'),
       (2, 1, 'ROLE_USER'),
       (3, 2, 'ROLE_ADMIN'),
       (5, 2, 'ROLE_USER'),
       (6, 3, 'ROLE_ADMIN'),
       (7, 3, 'ROLE_USER');

