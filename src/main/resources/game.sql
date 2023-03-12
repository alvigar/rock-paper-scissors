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