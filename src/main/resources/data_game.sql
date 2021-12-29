DELETE FROM game;

INSERT INTO game(id, name, uri) VALUES (1, 'Age of Empires IV', 'age-of-empires-iv');
INSERT INTO game(id, name, uri) VALUES (2, 'Europa Universalis IV', 'europa-universalis-iv');
INSERT INTO game(id, name, uri) VALUES (3, 'World of Warcraft', 'world-of-warcraft');
INSERT INTO game(id, name, uri) VALUES (4, 'Test Name', 'test-name');

DELETE FROM game_details;

INSERT INTO game_details(game_id, description, webpage, rating_players, rating_reviewer)
    VALUES (1,
        'Age of Empires IV jest czwartą odsłoną bestsellerowego cyklu RTS-ów, ' ||
        'nad którym pieczę wydawniczą sprawuje firma Microsoft. W przeciwieństwie do ' ||
        'poprzednich części serii, tworzonych przez zespół Ensemble Studios, produkcja ' ||
        'została opracowana przez studio Relic Entertainment, czyli autorów dwóch ' ||
        'pierwszych gier spod szyldu Homeworld, serii Warhammer 40,000: Dawn of War' ||
        ' oraz cyklu Company of Heroes.',
        'https://www.ageofempires.com/games/age-of-empires-iv/', 10.0, 9.5);

INSERT INTO game_details(game_id, description, webpage, rating_players, rating_reviewer)
    VALUES (2,
        'Czwarta odsłona cieszącego się dużą popularnością cyklu gier strategicznych ' ||
        'Europa Universalis. Tytuł wyprodukowany został przez' ||
        ' ekipę deweloperską Paradox Development Studio.',
        'https://www.paradoxplaza.com/europa-universalis-all/', 9.0, 8.5);

INSERT INTO game_details(game_id, description, webpage, rating_players, rating_reviewer)
    VALUES (3,
        'Epicka gra z gatunku MMORPG, której akcję osadzono w uniwersum znanym ' ||
        'z kultowej serii RTS-ów fantasy firmy Blizzard.',
        'https://worldofwarcraft.com/en-us/', 8.0, 7.5);

INSERT INTO game_details(game_id, description, webpage, rating_players, rating_reviewer)
    VALUES (4,
        'Test description',
        'https://test-link.com', 7.0, 6.5);

DELETE FROM platform;

INSERT INTO platform(id, name, uri) VALUES (1, 'PC', 'pc');
INSERT INTO platform(id, name, uri) VALUES (2, 'XBOX 360', 'xbox-360');
INSERT INTO platform(id, name, uri) VALUES (3, 'Playstation 5', 'playstation-5');

DELETE FROM game_platform;

INSERT INTO game_platform(game_id, platform_id, release_date) VALUES (1, 1, '2021-10-28');
INSERT INTO game_platform(game_id, platform_id, release_date) VALUES (1, 2, '2021-10-30');
INSERT INTO game_platform(game_id, platform_id, release_date) VALUES (2, 1, '2013-08-13');
INSERT INTO game_platform(game_id, platform_id, release_date) VALUES (3, 3, '2005-02-11');
INSERT INTO game_platform(game_id, platform_id, release_date) VALUES (4, 1, '2000-01-01');
INSERT INTO game_platform(game_id, platform_id, release_date) VALUES (4, 2, '2000-05-06');

DELETE FROM company;

INSERT INTO company(id, name) VALUES (1, 'Relic Entertainment');
INSERT INTO company(id, name) VALUES (2, 'World''s Edge');
INSERT INTO company(id, name) VALUES (3, 'Paradox Development Studio');
INSERT INTO company(id, name) VALUES (4, 'Blizzard Entertainment');
INSERT INTO company(id, name) VALUES (5, 'Test Producer');
INSERT INTO company(id, name) VALUES (6, 'Xbox Game Studios / Microsoft Studios');
INSERT INTO company(id, name) VALUES (7, 'Paradox Interactive');
INSERT INTO company(id, name) VALUES (8, 'Test Publisher');

DELETE FROM company_general_type;

INSERT INTO company_general_type(id, type) VALUES (1, 'producer');
INSERT INTO company_general_type(id, type) VALUES (2, 'publisher');

DELETE FROM company_type;

INSERT INTO company_type(company_id, type_id) VALUES (1, 1);
INSERT INTO company_type(company_id, type_id) VALUES (2, 1);
INSERT INTO company_type(company_id, type_id) VALUES (3, 1);
INSERT INTO company_type(company_id, type_id) VALUES (4, 1);
INSERT INTO company_type(company_id, type_id) VALUES (4, 2);
INSERT INTO company_type(company_id, type_id) VALUES (5, 1);
INSERT INTO company_type(company_id, type_id) VALUES (6, 2);
INSERT INTO company_type(company_id, type_id) VALUES (7, 2);
INSERT INTO company_type(company_id, type_id) VALUES (8, 2);

DELETE FROM game_company;

INSERT INTO game_company(game_id, company_id) VALUES(1, 1);
INSERT INTO game_company(game_id, company_id) VALUES(1, 2);
INSERT INTO game_company(game_id, company_id) VALUES(2, 3);
INSERT INTO game_company(game_id, company_id) VALUES(3, 4);
INSERT INTO game_company(game_id, company_id) VALUES(4, 5);
INSERT INTO game_company(game_id, company_id) VALUES(1, 6);
INSERT INTO game_company(game_id, company_id) VALUES(2, 7);
INSERT INTO game_company(game_id, company_id) VALUES(4, 8);
