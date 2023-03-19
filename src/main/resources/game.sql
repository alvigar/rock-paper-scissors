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

CREATE TABLE IF NOT EXISTS role_detail (
    id INT GENERATED ALWAYS AS IDENTITY,
    role_name VARCHAR NOT NULL,
    PRIMARY KEY (id)
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
)