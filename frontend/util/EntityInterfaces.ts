export type RecurrenceInterval = 'daily' | 'weekly' | 'monthly' | 'yearly';

export interface Task {
    taskId: number;                     // task_id
    assignedTo?: number | null;        // assigned_to (nullable, -1 = everyone)
    doneBy?: number | null;            // done_by (nullable)
    timeEstimate: string;              // time_estimate, in HH:MM:SS format
    description: string;               // description text
    isRecurring: boolean;              // is_recurring
    recurrenceInterval?: RecurrenceInterval | null; // recurrence_interval enum
    status: string;                    // status string, default 'pending'
    createdAt: string;                 // created_at timestamp (ISO string)
    completedAt?: string | null;       // completed_at timestamp or null
}

export interface Team {
    teamId: number;           // team_id
    teamName: string;         // team_name
    teamOwner: number;        // team_owner (FK to users.user_id)
    createdAt: string;        // created_at timestamp (ISO string)
    tasks: Task[];            // array of Task objects
}