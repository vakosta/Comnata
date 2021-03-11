/* ========== ROOMS ========== */

CREATE TABLE rooms
(
    id            INT       NOT NULL,
    creation_date TIMESTAMP NOT NULL DEFAULT NOW(),

    PRIMARY KEY (id)
);

CREATE TABLE videos
(
    id       INT         NOT NULL,
    name     VARCHAR(20) NOT NULL,
    status   SMALLINT    NOT NULL,
    priority SMALLINT    NOT NULL,
    room     INT         NOT NULL REFERENCES rooms (id)
        ON DELETE SET NULL ON UPDATE CASCADE,

    PRIMARY KEY (id)
);

CREATE TABLE messages
(
    id            INT          NOT NULL,
    text          VARCHAR(100) NOT NULL,
    creation_date TIMESTAMP    NOT NULL DEFAULT NOW(),
    room          INT          NOT NULL REFERENCES rooms (id)
        ON DELETE SET NULL ON UPDATE CASCADE,

    PRIMARY KEY (id)
);

INSERT INTO rooms (id)
VALUES (1),
       (2);

INSERT INTO videos (id, name, status, priority, room)
VALUES (1, 'video1', 1, 2, 1),
       (2, 'video2', 1, 1, 2),
       (3, 'video3', 1, 1, 1);

INSERT INTO messages (id, text, room)
VALUES (1, 'kek', 1),
       (2, 'cheburek', 1);

/* ========== USERS ========== */

CREATE TABLE users
(
    id                INT         NOT NULL,
    username          VARCHAR(50) NOT NULL,
    password          CHAR(80)    NOT NULL,
    email             VARCHAR(80)          DEFAULT NULL,
    room              INT REFERENCES rooms (id)
        ON DELETE SET NULL ON UPDATE CASCADE,

    registration_date TIMESTAMP   NOT NULL DEFAULT NOW(),

    PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id   INT          NOT NULL,
    name VARCHAR(100) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE users_to_roles
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,

    PRIMARY KEY (user_id, role_id),

    CONSTRAINT fk_user_id_01 FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT fk_role_id FOREIGN KEY (role_id)
        REFERENCES roles (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE rooms_to_users
(
    room_id INT NOT NULL REFERENCES rooms (id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    user_id INT NOT NULL REFERENCES users (id)
        ON DELETE CASCADE ON UPDATE CASCADE,

    PRIMARY KEY (room_id, user_id)
);

INSERT INTO users (id, username, password, email)
VALUES (1, 'Vakosta', 'password', 'zzoorm@gmail.com'),
       (2, 'DarkSky', 'password', 'DarkSkyYes@yandex.ru'),
       (3, 'Sithell', 'password', 'sithellYo@gmail.com'),
       (4, 'Random_chel', 'password', 'chel@ya.ru');

INSERT INTO roles (id, name)
VALUES (1, 'USER'),
       (2, 'ADMIN'),
       (3, 'MODERATOR');

INSERT INTO users_to_roles (user_id, role_id)
VALUES (1, 1),
       (2, 2),
       (3, 2),
       (4, 3);