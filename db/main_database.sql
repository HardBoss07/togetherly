-- Use the database
CREATE DATABASE IF NOT EXISTS togetherly;
USE togetherly;

-- Start transaction to ensure atomic schema changes
START TRANSACTION;

-- Drop tables in reverse dependency order to avoid FK conflicts on drop
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS team_memberships;
DROP TABLE IF EXISTS teams;
DROP TABLE IF EXISTS users;

-- Users table: stores individual user accounts
CREATE TABLE users
(
    user_id    INT AUTO_INCREMENT PRIMARY KEY,     -- Unique user identifier
    username   VARCHAR(50)  NOT NULL UNIQUE,       -- Unique username
    password   VARCHAR(255) NOT NULL,              -- Hashed password
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- When user was created
);

-- Teams table: stores teams/groups owned by a user
CREATE TABLE teams
(
    team_id    INT AUTO_INCREMENT PRIMARY KEY,      -- Unique team identifier
    team_owner INT NOT NULL,                        -- FK to users.user_id (team creator/owner)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- When team was created
    FOREIGN KEY (team_owner) REFERENCES users (user_id)
        ON DELETE CASCADE                           -- If owner deleted, delete team
);

-- Team memberships: links users to teams (many-to-many relationship)
CREATE TABLE team_memberships
(
    team_id   INT NOT NULL,                        -- FK to teams.team_id
    user_id   INT NOT NULL,                        -- FK to users.user_id
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- When user joined the team
    PRIMARY KEY (team_id, user_id),                -- Composite PK prevents duplicate memberships
    FOREIGN KEY (team_id) REFERENCES teams (team_id)
        ON DELETE CASCADE,                         -- If team deleted, remove memberships
    FOREIGN KEY (user_id) REFERENCES users (user_id)
        ON DELETE CASCADE                          -- If user deleted, remove memberships
);

-- Tasks table: stores tasks linked to teams and assigned users
CREATE TABLE tasks
(
    task_id             INT AUTO_INCREMENT PRIMARY KEY,                                          -- Unique task identifier
    team_fk             INT       NOT NULL,                                                      -- FK to teams.team_id
    assigned_to         INT                                           DEFAULT NULL,              -- FK to users.user_id, NULL means assigned to @everyone in the team
    done_by             INT                                           DEFAULT NULL,              -- FK to users.user_id, user who completed the task (nullable)
    time_estimate       TIME,                                                                    -- Estimated time to complete (e.g., '01:30:00')
    description         TEXT,                                                                    -- Task description/details
    is_recurring        BOOLEAN                                       DEFAULT FALSE,             -- Is this a recurring task?
    recurrence_interval ENUM ('daily', 'weekly', 'monthly', 'yearly') DEFAULT NULL,              -- Recurrence frequency
    status              VARCHAR(20)                                   DEFAULT 'pending',         -- Task status (pending, in_progress, completed, etc.)
    created_at          TIMESTAMP                                     DEFAULT CURRENT_TIMESTAMP, -- Task creation timestamp
    completed_at        TIMESTAMP NULL                                DEFAULT NULL,              -- When task was marked completed

    FOREIGN KEY (team_fk) REFERENCES teams (team_id)
        ON DELETE CASCADE,                                                                       -- Delete tasks if team is deleted
    FOREIGN KEY (assigned_to) REFERENCES users (user_id)
        ON DELETE SET NULL,                                                                      -- If assigned user deleted, set assigned_to to NULL (@everyone)
    FOREIGN KEY (done_by) REFERENCES users (user_id)
        ON DELETE SET NULL                                                                       -- If completing user deleted, clear done_by
);

-- Commit all changes atomically
COMMIT;
