# Example Data Model for Togetherly

This section demonstrates example data for the core tables in the Togetherly schema: `users`, `teams`,
`team_memberships`, and `tasks`. It highlights how foreign key relationships are used.

## Users

| user\_id | username | password (hashed, shortened)            |
|----------|----------|-----------------------------------------|
| -1       | everyone | `$2a$12$QWZE1h/DEx5P1ml7AXENM...wxyLz2` |
| 1        | user1    | `$2a$12$6R4OECjFmsGtlj8iDsBLf...PdFToi` |
| 2        | user2    | `$2a$12$xyVLdZcSBsiCTQzJTmf1X...fbG8UK` |
| 3        | user3    | `$2a$12$2gCJLt3/VE0nSZQjXt/DY...JOu6F2` |

## Teams

| team\_id | team\_name  | team\_owner (FK to `users.user_id`) |
|----------|-------------|-------------------------------------|
| 1        | Test Team 1 | 1                                   |

## Team Memberships

| team\_id (FK) | user\_id (FK) |
|---------------|---------------|
| 1             | 1             |
| 1             | 2             |
| 1             | 3             |

## Tasks

| task\_id | team\_fk (FK) | assigned\_to (FK) | done\_by | time\_estimate | description         | is\_recurring | recurrence\_interval | status  |
|----------|---------------|-------------------|----------|----------------|---------------------|---------------|----------------------|---------|
| 1        | 1             | 2                 | NULL     | 00:30:00       | Walk the family dog | TRUE          | daily                | pending |
| 2        | 1             | 3                 | NULL     | 01:00:00       | Clean the bathrooms | TRUE          | weekly               | pending |
| 3        | 1             | -1                | NULL     | 00:15:00       | Check mail box      | FALSE         | NULL                 | pending |

> Note: `assigned_to = -1` is a special case meaning "assigned to everyone". This is backed by a special user with `user_id = -1`.