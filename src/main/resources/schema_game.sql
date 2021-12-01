CREATE TABLE IF NOT EXISTS game (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    uri VARCHAR(128) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS game_details (
    game_id INT NOT NULL,
    description TEXT NOT NULL,
    webpage VARCHAR(256),
    rating_players DECIMAL(4, 2),
    rating_reviewer DECIMAL(4, 2)
);

ALTER TABLE game_details ADD FOREIGN KEY (game_id) REFERENCES game(id);

CREATE TABLE IF NOT EXISTS platform (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS game_platform (
    game_id INT,
    platform_id INT,
    PRIMARY KEY(game_id, platform_id)
);

ALTER TABLE game_platform ADD FOREIGN KEY (game_id) REFERENCES game(id);
ALTER TABLE game_platform ADD FOREIGN KEY (platform_id) REFERENCES platform(id);

CREATE TABLE IF NOT EXISTS game_release_date (
    game_id INT NOT NULL,
    game_platform_id INT NOT NULL,
    release_date DATE
);

ALTER TABLE game_release_date ADD FOREIGN KEY (game_id) REFERENCES game(id);
ALTER TABLE game_release_date ADD FOREIGN KEY (game_platform_id) REFERENCES platform(id);

CREATE TABLE IF NOT EXISTS producer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    producer_name VARCHAR(128)
);

CREATE TABLE IF NOT EXISTS game_producer (
    game_id INT NOT NULL,
    producer_id INT NOT NULL
);

ALTER TABLE game_producer ADD FOREIGN KEY (game_id) REFERENCES game(id);
ALTER TABLE game_producer ADD FOREIGN KEY (producer_id) REFERENCES producer(id);

CREATE TABLE IF NOT EXISTS publisher (
    id INT AUTO_INCREMENT PRIMARY KEY,
    publisher_name VARCHAR(128)
);

CREATE TABLE IF NOT EXISTS game_publisher (
    game_id INT NOT NULL,
    publisher_id INT NOT NULL
);

ALTER TABLE game_publisher ADD FOREIGN KEY (game_id) REFERENCES game(id);
ALTER TABLE game_publisher ADD FOREIGN KEY (publisher_id) REFERENCES publisher(id);