DELETE FROM users;

INSERT INTO users (username, password) VALUES ('user1', '$2a$10$zfHUDQhX73PYdbjZefPx/OiJEn/xWciiSHAZiwLH2Rn7DtonnNrSW');
INSERT INTO users (username, password) VALUES ('user2', '$2a$10$vcHcQBKE4e8ZMcLewCOxROJ1mjSZsl/h6jliNcoBNm/1ksvzf/Fk2');
INSERT INTO users (username, password) VALUES ('admin', '$2a$10$7abF1B3Inxl3aidh4s74vOYLtFCfkE9LVFwqzh0Yd2HcrPxpo26YS');

DELETE FROM authority_types;

INSERT INTO authority_types (authority_name) VALUES ('USER');
INSERT INTO authority_types (authority_name) VALUES ('ADMIN');

DELETE FROM authorities;

INSERT INTO authorities (username, authority) VALUES ('user1', 'USER');
INSERT INTO authorities (username, authority) VALUES ('user2', 'USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ADMIN');

-- TODO add values for groups, group_authorities and group_members