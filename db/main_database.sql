-- Create and use the database
CREATE DATABASE IF NOT EXISTS togetherly;
USE togetherly;

-- Start transaction
START TRANSACTION;

-- Drop tables in reverse dependency order
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS team_memberships;
DROP TABLE IF EXISTS teams;
DROP TABLE IF EXISTS users;

-- Users table
CREATE TABLE users
(
    user_id    BIGINT AUTO_INCREMENT PRIMARY KEY, -- Use BIGINT for compatibility with Java long
    username   VARCHAR(50)  NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Teams table
CREATE TABLE teams
(
    team_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    team_owner BIGINT NOT NULL, -- FK to users.user_id
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (team_owner) REFERENCES users (user_id)
        ON DELETE CASCADE
);

-- Team memberships (many-to-many users <-> teams)
CREATE TABLE team_memberships
(
    team_id   BIGINT NOT NULL,
    user_id   BIGINT NOT NULL,
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (team_id, user_id),
    FOREIGN KEY (team_id) REFERENCES teams (team_id)
        ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
        ON DELETE CASCADE
);

-- Tasks table
CREATE TABLE tasks
(
    task_id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    team_fk             BIGINT    NOT NULL,                                         -- FK to teams.team_id
    assigned_to         BIGINT                                        DEFAULT NULL, -- FK to users.user_id (including -1 for everyone)
    done_by             BIGINT                                        DEFAULT NULL, -- FK to users.user_id
    time_estimate       TIME,                                                       -- e.g., '01:30:00'
    description         TEXT,
    is_recurring        BOOLEAN                                       DEFAULT FALSE,
    recurrence_interval ENUM ('daily', 'weekly', 'monthly', 'yearly') DEFAULT NULL,
    status              VARCHAR(20)                                   DEFAULT 'pending',
    created_at          TIMESTAMP                                     DEFAULT CURRENT_TIMESTAMP,
    completed_at        TIMESTAMP NULL                                DEFAULT NULL,

    FOREIGN KEY (team_fk) REFERENCES teams (team_id)
        ON DELETE CASCADE,
    FOREIGN KEY (assigned_to) REFERENCES users (user_id)
        ON DELETE SET NULL,
    FOREIGN KEY (done_by) REFERENCES users (user_id)
        ON DELETE SET NULL
);

-- Commit all changes
COMMIT;

-- User creation and permission setup
CREATE USER IF NOT EXISTS 'togetherly_admin'@'%' IDENTIFIED BY 'togetherly_admin';
GRANT ALL PRIVILEGES ON togetherly.* TO 'togetherly_admin'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;

CREATE USER IF NOT EXISTS 'togetherly_backend'@'%' IDENTIFIED BY 'togetherly_backend';
GRANT ALL PRIVILEGES ON togetherly.* TO 'togetherly_backend'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;