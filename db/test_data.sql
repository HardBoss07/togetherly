USE togetherly;

-- Insert special 'everyone' user with user_id = -1
INSERT INTO users (user_id, username, password)
VALUES (-1, 'everyone', 'everyone');

-- Insert normal users, auto_increment user_id
INSERT INTO users (username, password)
VALUES ('user1', 'user1'),
       ('user2', 'user2'),
       ('user3', 'user3');

-- Insert team owned by user1
INSERT INTO teams (team_owner)
VALUES ((SELECT user_id FROM users WHERE username = 'user1'));

-- Get the created team_id
SET @teamId = LAST_INSERT_ID();

-- Add user1, user2, and user3 as members of the team
INSERT INTO team_memberships (team_id, user_id)
SELECT @teamId, user_id
FROM users
WHERE username IN ('user1', 'user2', 'user3');

-- Insert tasks for the team
INSERT INTO tasks (team_fk, assigned_to, done_by, time_estimate, description, is_recurring, recurrence_interval, status)
VALUES (@teamId,
        (SELECT user_id FROM users WHERE username = 'user2'),
        NULL,
        '00:30:00',
        'Walk the family dog',
        TRUE,
        'daily',
        'pending'),
       (@teamId,
        (SELECT user_id FROM users WHERE username = 'user3'),
        NULL,
        '01:00:00',
        'Clean the bathrooms',
        TRUE,
        'weekly',
        'pending'),
       (@teamId,
        -1, -- assigned to everyone
        NULL,
        '00:15:00',
        'Check mail box',
        FALSE,
        NULL,
        'pending');
