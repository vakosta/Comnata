/* ========== ROOMS ========== */

CREATE TABLE room
(
    id            BIGSERIAL   NOT NULL,
    name          VARCHAR(50) NOT NULL,
    creation_date TIMESTAMP   NOT NULL DEFAULT NOW(),

    PRIMARY KEY (id)
);


/* ========== USERS ========== */

CREATE TABLE app_user
(
    id       BIGSERIAL   NOT NULL,
    username VARCHAR(30) NOT NULL,
    room_id  INTEGER     NOT NULL,

    PRIMARY KEY (id),

    FOREIGN KEY (room_id) REFERENCES room (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


/* ========== INSERTS ========== */

/*INSERT INTO room (id, name)
VALUES (1, '1'),
       (2, '2');

INSERT INTO app_user (id, username, room_id)
VALUES (1, 'Vakosta', 1),
       (2, 'DarkSky', 2),
       (3, 'Sithell', 1);*/
