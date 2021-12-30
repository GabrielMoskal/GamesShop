CREATE TABLE IF NOT EXISTS game (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(128) UNIQUE NOT NULL,
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
    name VARCHAR(50) UNIQUE NOT NULL,
    uri VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS game_platform (
    game_id INT,
    platform_id INT,
    release_date DATE NOT NULL,
    PRIMARY KEY(game_id, platform_id)
);

ALTER TABLE game_platform ADD FOREIGN KEY (game_id) REFERENCES game(id);
ALTER TABLE game_platform ADD FOREIGN KEY (platform_id) REFERENCES platform(id);

CREATE TABLE IF NOT EXISTS company (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(128) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS company_general_type (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS company_type (
    company_id INT NOT NULL,
    type_id INT NOT NULL
);

ALTER TABLE company_type ADD FOREIGN KEY (company_id) REFERENCES company(id);
ALTER TABLE company_type ADD FOREIGN KEY (type_id) REFERENCES company_general_type(id);

CREATE TABLE IF NOT EXISTS game_company (
    game_id INT NOT NULL,
    company_id INT NOT NULL
);