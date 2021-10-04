CREATE TABLE USER
(
    id       Long ,
    user_name varchar(255),
    password varchar(255)
);

INSERT INTO USER(id, user_name, password)
VALUES (1, 'admin', ''),
       (2, 'Marcin', '');

CREATE TABLE USER_ROLE
(
    id      Long,
    user_id Long,
    role    varchar(255)
);

INSERT INTO USER_ROLE (id, user_id, role)
VALUES (1, 1, 'ROLE_ADMIN'),
        (2, 1, 'ROLE_USER'),
        (3, 2, 'ROLE_USER');

