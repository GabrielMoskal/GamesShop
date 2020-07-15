DELETE FROM users;

INSERT INTO users (username, password) VALUES ('user1', 'password1');
INSERT INTO users (username, password) VALUES ('user2', 'password2');
INSERT INTO users (username, password) VALUES ('admin', 'password3');

DELETE FROM authority_types;

INSERT INTO authority_types (authority_name) VALUES ('USER');
INSERT INTO authority_types (authority_name) VALUES ('ADMIN');

DELETE FROM authorities;

INSERT INTO authorities (username, authority) VALUES ('user1', 'USER');
INSERT INTO authorities (username, authority) VALUES ('user2', 'USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ADMIN');

-- TODO add values for groups, group_authorities and group_members