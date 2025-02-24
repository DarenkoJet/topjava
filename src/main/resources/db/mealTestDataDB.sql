DELETE
FROM meals;

INSERT INTO meals (datetime, description, calories, userid)
VALUES ('2025-02-22 08:00:00', 'Завтрак', 1000, 100000),
       ('2025-02-22 13:00:00', 'Обед', 700, 100000),
       ('2025-02-22 18:00:00', 'Ужин', 500, 100000);

INSERT INTO meals (datetime, description, calories, userid)
VALUES ('2025-02-21 08:00:00', 'Завтрак', 1000, 100001),
       ('2025-02-21 13:00:00', 'Обед', 700, 100001),
       ('2025-02-21 18:00:00', 'Ужин', 500, 100001);