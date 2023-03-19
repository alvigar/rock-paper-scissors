CREATE TABLE IF NOT EXISTS game (
    id INT GENERATED ALWAYS AS IDENTITY,
    user_name VARCHAR NOT NULL,
    winner BOOL,
    winner_player VARCHAR,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS movement (
    id INT GENERATED ALWAYS AS IDENTITY,
    player VARCHAR NOT NULL,
    figure VARCHAR NOT NULL,
    game_id INT,
    PRIMARY KEY (id),
    CONSTRAINT fk_game
        FOREIGN KEY (game_id)
            REFERENCES game(id)
);

CREATE TABLE IF NOT EXISTS user_detail (
    id INT GENERATED ALWAYS AS IDENTITY,
    nickname VARCHAR NOT NULL,
    user_password VARCHAR NOT NULL,
    enabled BOOL NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO user_detail (nickname, user_password, enabled)
SELECT 'alfonso', '$2a$10$EcE2mGEUMYtSfHAzG6nC..lzNg6ZoHdFB.ESpek2bSGSWQuuQZP0K', true
WHERE NOT EXISTS (
    SELECT id FROM user_detail WHERE nickname = 'alfonso'
);

CREATE TABLE IF NOT EXISTS role_detail (
    id INT GENERATED ALWAYS AS IDENTITY,
    role_name VARCHAR NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO role_detail (role_name)
SELECT 'USER'
WHERE NOT EXISTS (
    SELECT id FROM role_detail WHERE role_name = 'USER'
);
INSERT INTO role_detail (role_name)
SELECT 'ADMIN'
    WHERE NOT EXISTS (
    SELECT id FROM role_detail WHERE role_name = 'ADMIN'
);

CREATE TABLE IF NOT EXISTS user_role (
    id_user INT NOT NULL,
    id_role INT NOT NULL,
    PRIMARY KEY (id_user, id_role),
    CONSTRAINT fk_user
        FOREIGN KEY (id_user)
            REFERENCES user_detail(id),
    CONSTRAINT fk_role
        FOREIGN KEY (id_role)
            REFERENCES role_detail(id)
);

INSERT INTO user_role (id_user, id_role)
VALUES (1, 1), (1, 2) ON CONFLICT DO NOTHING;