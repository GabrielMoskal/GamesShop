DELETE FROM game;

INSERT INTO game(game_name, uri) VALUES ('Age of Empires IV', 'age-of-empires-iv');
INSERT INTO game(game_name, uri) VALUES ('Europa Universalis IV', 'europa-universalis-iv');
INSERT INTO game(game_name, uri) VALUES ('World of Warcraft', 'world-of-warcraft');
INSERT INTO game(game_name, uri) VALUES ('Test Name', 'test-name');

DELETE FROM game_description;

INSERT INTO game_description(id, description, webpage, rating_players, rating_reviewer)
    VALUES (1,
        'Age of Empires IV jest czwartą odsłoną bestsellerowego cyklu RTS-ów, ' ||
        'nad którym pieczę wydawniczą sprawuje firma Microsoft. W przeciwieństwie do ' ||
        'poprzednich części serii, tworzonych przez zespół Ensemble Studios, produkcja ' ||
        'została opracowana przez studio Relic Entertainment, czyli autorów dwóch ' ||
        'pierwszych gier spod szyldu Homeworld, serii Warhammer 40,000: Dawn of War' ||
        ' oraz cyklu Company of Heroes.',
        'https://www.ageofempires.com/games/age-of-empires-iv/', 10.0, 9.5);

INSERT INTO game_description(id, description, webpage, rating_players, rating_reviewer)
    VALUES (2,
        'Czwarta odsłona cieszącego się dużą popularnością cyklu gier strategicznych ' ||
        'Europa Universalis. Tytuł wyprodukowany został przez' ||
        ' ekipę deweloperską Paradox Development Studio.',
        'https://www.paradoxplaza.com/europa-universalis-all/', 9.0, 8.5);

INSERT INTO game_description(id, description, webpage, rating_players, rating_reviewer)
    VALUES (3,
        'Epicka gra z gatunku MMORPG, której akcję osadzono w uniwersum znanym ' ||
        'z kultowej serii RTS-ów fantasy firmy Blizzard.',
        'https://worldofwarcraft.com/en-us/', 8.0, 7.5);

INSERT INTO game_description(id, description, webpage, rating_players, rating_reviewer)
    VALUES (4,
        'Test short description',
        'https://test-link.com', 7.0, 6.5);

DELETE FROM platform;

INSERT INTO platform(id, platform_name) VALUES (1, 'PC');
INSERT INTO platform(id, platform_name) VALUES (2, 'XBOX 360');
INSERT INTO platform(id, platform_name) VALUES (3, 'Playstation 5');

DELETE FROM game_platform;

INSERT INTO game_platform(game_id, platform_id) VALUES (1, 1);
INSERT INTO game_platform(game_id, platform_id) VALUES (1, 2);
INSERT INTO game_platform(game_id, platform_id) VALUES (2, 1);
INSERT INTO game_platform(game_id, platform_id) VALUES (3, 3);
INSERT INTO game_platform(game_id, platform_id) VALUES (4, 1);
INSERT INTO game_platform(game_id, platform_id) VALUES (4, 2);

DELETE FROM game_release_date;

INSERT INTO game_release_date(game_id, game_platform_id, release_date) VALUES (1, 1, '2021-10-28');
INSERT INTO game_release_date(game_id, game_platform_id, release_date) VALUES (1, 2, '2021-10-30');
INSERT INTO game_release_date(game_id, game_platform_id, release_date) VALUES (2, 1, '2013-08-13');
INSERT INTO game_release_date(game_id, game_platform_id, release_date) VALUES (3, 3, '2005-02-11');
INSERT INTO game_release_date(game_id, game_platform_id, release_date) VALUES (4, 1, '2000-01-01');

DELETE FROM producer;

INSERT INTO producer(id, producer_name) VALUES (1, 'Relic Entertainment');
INSERT INTO producer(id, producer_name) VALUES (2, 'World''s Edge');
INSERT INTO producer(id, producer_name) VALUES (3, 'Paradox Development Studio');
INSERT INTO producer(id, producer_name) VALUES (4, 'Blizzard Entertainment');
INSERT INTO producer(id, producer_name) VALUES (5, 'Test Producer');

DELETE FROM game_producer;

INSERT INTO game_producer(game_id, producer_id) VALUES (1, 1);
INSERT INTO game_producer(game_id, producer_id) VALUES (1, 2);
INSERT INTO game_producer(game_id, producer_id) VALUES (2, 3);
INSERT INTO game_producer(game_id, producer_id) VALUES (3, 4);
INSERT INTO game_producer(game_id, producer_id) VALUES (4, 5);

DELETE FROM publisher;

INSERT INTO publisher(id, publisher_name) VALUES (1, 'Xbox Game Studios / Microsoft Studios');
INSERT INTO publisher(id, publisher_name) VALUES (2, 'Paradox Interactive');
INSERT INTO publisher(id, publisher_name) VALUES (3, 'Blizzard Entertainment');
INSERT INTO publisher(id, publisher_name) VALUES (4, 'Test Publisher');

DELETE FROM game_publisher;

INSERT INTO game_publisher(game_id, publisher_id) VALUES (1, 1);
INSERT INTO game_publisher(game_id, publisher_id) VALUES (2, 2);
INSERT INTO game_publisher(game_id, publisher_id) VALUES (3, 3);
INSERT INTO game_publisher(game_id, publisher_id) VALUES (4, 4);