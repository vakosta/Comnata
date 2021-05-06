/* ========== ROOMS ========== */

CREATE TABLE room
(
    id            BIGINT    NOT NULL,
    name          VARCHAR(50),
    creation_date TIMESTAMP NOT NULL DEFAULT NOW(),

    PRIMARY KEY (id)
);

CREATE TABLE video
(
    uuid     VARCHAR(100) NOT NULL,
    name     VARCHAR(20)  NOT NULL,
    priority SMALLINT     NOT NULL,
    status   SMALLINT     NOT NULL,
    progress SMALLINT,
    room_id  INT          NOT NULL,

    PRIMARY KEY (uuid),

    FOREIGN KEY (room_id) REFERENCES room (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE message
(
    id            BIGINT       NOT NULL,
    text          VARCHAR(100) NOT NULL,
    creation_date TIMESTAMP    NOT NULL DEFAULT NOW(),
    room_id       INT          NOT NULL,

    PRIMARY KEY (id),

    FOREIGN KEY (room_id) REFERENCES room (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


/* ========== USERS ========== */

CREATE TABLE app_user
(
    id                BIGINT      NOT NULL,
    username          VARCHAR(30) NOT NULL,
    password          CHAR(80)    NOT NULL,
    email             VARCHAR(80),
    picture           VARCHAR(20),
    room_id           INT,

    registration_date TIMESTAMP   NOT NULL DEFAULT NOW(),

    PRIMARY KEY (id),

    FOREIGN KEY (room_id) REFERENCES room (id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

CREATE TABLE role
(
    id   BIGINT       NOT NULL,
    name VARCHAR(100) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE user_to_role
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,

    PRIMARY KEY (user_id, role_id),

    CONSTRAINT fk_user_id_01 FOREIGN KEY (user_id) REFERENCES app_user (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES role (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


/* ========== INSERTS ========== */

INSERT INTO room (id)
VALUES (1),
       (2);

INSERT INTO video (uuid, name, status, priority, room_id)
VALUES ('123', 'video1', 1, 2, 1),
       ('321', 'video2', 1, 1, 2),
       ('213', 'video3', 1, 1, 1);

INSERT INTO message (id, text, room_id)
VALUES (1, 'kek', 1),
       (2, 'cheburek', 1);

INSERT INTO app_user (id, username, password, email, room_id)
VALUES (1, 'Vakosta', 'password', 'zzoorm@gmail.com', 1),
       (2, 'DarkSky', 'password', 'DarkSkyYes@yandex.ru', null),
       (3, 'Sithell', 'password', 'sithellYo@gmail.com', null),
       (4, 'Random_chel', 'password', 'chel@ya.ru', null);

INSERT INTO role (id, name)
VALUES (1, 'USER'),
       (2, 'ADMIN'),
       (3, 'MODERATOR');

INSERT INTO user_to_role (user_id, role_id)
VALUES (1, 1),
       (2, 2),
       (3, 2),
       (4, 3);