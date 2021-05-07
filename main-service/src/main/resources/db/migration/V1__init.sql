CREATE TABLE room
(
    id            BIGSERIAL   NOT NULL,
    name          VARCHAR(50) NOT NULL,
    creation_date TIMESTAMP   NOT NULL DEFAULT NOW(),

    PRIMARY KEY (id)
);

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

CREATE TABLE action
(
    id        BIGSERIAL   NOT NULL,
    type      VARCHAR(15) NOT NULL,
    seek_time REAL        NOT NULL,
    step      VARCHAR(15) NOT NULL,
    room_id   BIGSERIAL   NOT NULL,
    author_id BIGSERIAL   NOT NULL,

    PRIMARY KEY (id),

    FOREIGN KEY (room_id) REFERENCES room (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    FOREIGN KEY (author_id) REFERENCES app_user (id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

CREATE TABLE user_to_action
(
    user_id   BIGSERIAL NOT NULL,
    action_id BIGSERIAL NOT NULL,

    PRIMARY KEY (user_id, action_id),

    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES app_user (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_role_id FOREIGN KEY (action_id) REFERENCES action (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
