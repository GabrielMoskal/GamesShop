DELETE FROM users;

INSERT INTO users (username, password) VALUES ('user1', /* password1 */'$2a$10$zfHUDQhX73PYdbjZefPx/OiJEn/xWciiSHAZiwLH2Rn7DtonnNrSW');
INSERT INTO users (username, password) VALUES ('user2', /* password2 */'$2a$10$vcHcQBKE4e8ZMcLewCOxROJ1mjSZsl/h6jliNcoBNm/1ksvzf/Fk2');
INSERT INTO users (username, password) VALUES ('admin', /* password1 */'$2a$10$zfHUDQhX73PYdbjZefPx/OiJEn/xWciiSHAZiwLH2Rn7DtonnNrSW');

DELETE FROM authority_types;

INSERT INTO authority_types (authority_name) VALUES ('ROLE_USER');
INSERT INTO authority_types (authority_name) VALUES ('ROLE_ADMIN');

DELETE FROM authorities;

INSERT INTO authorities (username, authority) VALUES ('user1', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('user2', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');

-- TODO add values for groups, group_authorities and group_members