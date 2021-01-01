CREATE TABLE users
(
    id       INT         NOT NULL,
    password CHAR(80)    NOT NULL,
    username VARCHAR(50) DEFAULT NULL,
    email    VARCHAR(80) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id   int NOT NULL,
    name varchar(100) DEFAULT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE users_roles
(
    user_id int NOT NULL,
    role_id int DEFAULT NULL,

    PRIMARY KEY (user_id, role_id),

    CONSTRAINT fk_user_id_01 FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT fk_role_id FOREIGN KEY (role_id)
        REFERENCES roles (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE rooms
(
    id   int NOT NULL,
    name varchar(100),

    PRIMARY KEY (id)
);

INSERT INTO users (id, username, password, email)
VALUES (1, 'Vakosta', 'password', 'zzoorm@gmail.com'),
       (2, 'DarkSky', 'password', 'DarkSkyYes@yandex.ru'),
       (3, 'Sithell', 'password', 'sithellYo@gmail.com'),
       (4, 'Random_chel', 'password', 'chel@ya.ru');

INSERT INTO roles (id, name)
VALUES (1, 'Администратор'),
       (2, 'Модератор'),
       (3, 'Пользователь');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (2, 2),
       (3, 2),
       (4, 3);