DELETE
FROM user_role;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2025-02-21 08:00:00', 'Завтрак USER', 200, 100000),
       ('2025-02-21 13:00:00', 'Обед USER', 650, 100000),
       ('2025-02-21 18:00:00', 'Ужин USER', 500, 100000),
       ('2025-02-22 08:00:00', 'Завтрак USER', 900, 100000),
       ('2025-02-22 13:00:00', 'Обед USER', 1200, 100000),
       ('2025-02-22 18:00:00', 'Ужин USER', 550, 100000),
       ('2025-02-25 13:00:00', 'Борщ', 1000, 100000),
       ('2025-02-24 00:00:00', 'Супер батончик', 10000, 100000);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2025-02-21 08:00:00', 'Завтрак ADMIN', 1000, 100001),
       ('2025-02-21 13:00:00', 'Обед ADMIN', 1500, 100001),
       ('2025-02-21 18:00:00', 'Ужин ADMIN', 400, 100001),
       ('2025-02-22 08:00:00', 'Завтрак ADMIN', 900, 100001),
       ('2025-02-22 13:00:00', 'Обед ADMIN', 700, 100001),
       ('2025-02-22 18:00:00', 'Ужин ADMIN', 500, 100001),
       ('2025-02-25 13:00:00', 'Борщ', 1000, 100001);