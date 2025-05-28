USE togetherly;

START TRANSACTION;

-- Insert users (password = username)
INSERT INTO users (username, password) VALUES
                                           ('user1', 'user1'),
                                           ('user2', 'user2'),
                                           ('user3', 'user3');

-- Create a team owned by user1
INSERT INTO teams (team_owner)
VALUES ((SELECT user_id FROM users WHERE username = 'user1'));

SET @team_id := LAST_INSERT_ID();

-- Add all users to the team
INSERT INTO team_memberships (team_id, user_id) VALUES
                                                    (@team_id, (SELECT user_id FROM users WHERE username = 'user1')),
                                                    (@team_id, (SELECT user_id FROM users WHERE username = 'user2')),
                                                    (@team_id, (SELECT user_id FROM users WHERE username = 'user3'));

-- Recurring daily task assigned to user2
INSERT INTO tasks (
    team_fk, assigned_to, description, time_estimate,
    status, is_recurring, recurrence_interval
) VALUES (
             @team_id,
             (SELECT user_id FROM users WHERE username = 'user2'),
             'Walk the family dog around the block',
             '00:30:00',
             'pending',
             TRUE,
             'daily'
         );

-- Recurring weekly task assigned to user3
INSERT INTO tasks (
    team_fk, assigned_to, description, time_estimate,
    status, is_recurring, recurrence_interval
) VALUES (
             @team_id,
             (SELECT user_id FROM users WHERE username = 'user3'),
             'Clean all the bathrooms thoroughly (sink, toilet, floor)',
             '01:00:00',
             'pending',
             TRUE,
             'weekly'
         );

-- Recurring weekly task assigned to @everyone (assigned_to NULL)
INSERT INTO tasks (
    team_fk, assigned_to, description, time_estimate,
    status, is_recurring, recurrence_interval
) VALUES (
             @team_id,
             NULL,
             'Take out the recycling bins to the curb (Thursdays)',
             '00:15:00',
             'pending',
             TRUE,
             'weekly'
         );

COMMIT;
