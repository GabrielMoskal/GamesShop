CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(72) NOT NULL,
    enabled TINYINT(1) NOT NULL DEFAULT 1,
    PRIMARY KEY(username)
);

CREATE TABLE IF NOT EXISTS authority_types (
    authority_name VARCHAR(50) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL
);

ALTER TABLE authorities ADD FOREIGN KEY (username) REFERENCES users(username);
ALTER TABLE authorities ADD FOREIGN KEY (authority) REFERENCES authority_types(authority_name);

CREATE TABLE IF NOT EXISTS groups (
    id INT PRIMARY KEY,
    group_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS group_authorities (
    group_id INT NOT NULL,
    authority VARCHAR(50) NOT NULL
);

ALTER TABLE group_authorities ADD FOREIGN KEY (group_id) REFERENCES groups(id);
ALTER TABLE group_authorities ADD FOREIGN KEY (authority) REFERENCES authority_types(authority_name);

CREATE TABLE IF NOT EXISTS group_members (
    id INT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    group_id int NOT NULL
);

ALTER TABLE group_members ADD FOREIGN KEY (group_id) REFERENCES groups(id);