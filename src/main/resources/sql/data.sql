INSERT INTO Users (username, firstname, lastname, birth_date, email, personal_info)
VALUES ('johndoe', 'John', 'Doe', '1990-05-15', 'john.doe@email.com', 'Software developer'),
       ('sarahsmith', 'Sarah', 'Smith', '1988-03-22', 'sarah.smith@email.com', 'Digital marketer'),
       ('mikejohnson', 'Mike', 'Johnson', '1992-11-08', 'mike.johnson@email.com', 'Tech blogger'),
       ('emilydavis', 'Emily', 'Davis', '1985-07-14', 'emily.davis@email.com', 'UX designer'),
       ('alexwilson', 'Alex', 'Wilson', '1993-12-03', 'alex.wilson@email.com', 'Full-stack developer');

INSERT INTO Posts (title, description, category, post_date, update_date, user_id)
VALUES ('Top 10 Songs of 2024', 'My personal ranking of the best songs this year', 'MUSIC', '2024-01-15 10:30:00', NULL,
        1),
       ('Solo Trip to Japan', 'Amazing experiences during my 2-week solo adventure in Japan', 'TRAVEL',
        '2024-01-18 09:15:00', NULL, 2),
       ('Homemade Pasta Recipe', 'Step-by-step guide to making perfect pasta from scratch', 'COOKING',
        '2024-01-20 16:45:00', '2024-01-21 10:30:00', 3),
       ('Marathon Training Tips', 'How I trained for my first marathon in 6 months', 'SPORT', '2024-01-22 11:20:00',
        NULL, 4),
       ('Stock Market Update', 'Analysis of current market trends and investment opportunities', 'FINANCES',
        '2024-01-25 13:10:00', NULL, 5),
       ('My New Year Resolutions', 'Personal goals and reflections for the year ahead', 'PERSONAL',
        '2024-02-01 14:30:00', NULL, 1),
       ('Breaking: Tech Industry News', 'Latest developments in AI and cryptocurrency markets', 'NEWS',
        '2024-02-05 11:15:00', NULL, 2);

INSERT INTO Comments (user_id, post_id, post_date, message)
VALUES (2, 1, '2024-01-15 12:45:00', 'Great music taste! I love half of these songs.'),
       (3, 1, '2024-01-15 14:20:00', 'Thanks for sharing. Added some to my playlist.'),
       (1, 2, '2024-01-18 15:30:00', 'Japan looks amazing! How was the food?'),
       (4, 3, '2024-01-21 09:15:00', 'This pasta recipe looks delicious. Trying it tonight!'),
       (5, 4, '2024-01-22 16:45:00', 'Inspiring! I''m thinking about running a half marathon.'),
       (2, 5, '2024-01-25 18:20:00', 'Solid financial analysis. Thanks for the insights.'),
       (3, 7, '2024-02-05 20:10:00', 'Important news update. Thanks for keeping us informed.');

INSERT INTO Reactions (user_id, post_id, type)
VALUES (2, 1, 'LIKE'),
       (3, 1, 'HEART'),
       (4, 1, 'LIKE'),
       (1, 2, 'HEART'),
       (3, 2, 'LIKE'),
       (5, 2, 'TROPHY'),
       (1, 3, 'HEART'),
       (2, 3, 'LIKE'),
       (4, 3, 'LIKE'),
       (5, 3, 'HEART'),
       (1, 4, 'TROPHY'),
       (3, 4, 'LIKE'),
       (5, 4, 'HEART'),
       (2, 5, 'LIKE'),
       (4, 5, 'DISLIKE'),
       (1, 6, 'HEART'),
       (2, 7, 'LIKE'),
       (5, 7, 'LIKE');
