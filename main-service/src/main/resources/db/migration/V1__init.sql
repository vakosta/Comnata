/* ========== ROOMS ========== */

CREATE TABLE room
(
    id            BIGSERIAL   NOT NULL,
    name          VARCHAR(10) NOT NULL,
    creation_date TIMESTAMP   NOT NULL DEFAULT NOW(),

    PRIMARY KEY (id)
);


/* ========== USERS ========== */

CREATE TABLE app_user
(
    id       BIGSERIAL   NOT NULL,
    username VARCHAR(50) NOT NULL,
    room_id  BIGSERIAL   NOT NULL,

    PRIMARY KEY (id),

    FOREIGN KEY (room_id) REFERENCES room (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
