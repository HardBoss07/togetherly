package ch.bosshard.matteo.togetherly.classes.entity;

import ch.bosshard.matteo.togetherly.enums.RecurrenceInterval;
import jakarta.persistence.*;

import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_fk", nullable = false)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "done_by")
    private User doneBy;

    @Column(name = "time_estimate")
    private Time timeEstimate;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_recurring", nullable = false)
    private Boolean isRecurring = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "recurrance_interval", columnDefinition = "ENUM('daily','weekly','monthly','yearly')")
    private RecurrenceInterval recurranceInterval;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "completed_at")
    private Timestamp completedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public User getDoneBy() {
        return doneBy;
    }

    public void setDoneBy(User doneBy) {
        this.doneBy = doneBy;
    }

    public Time getTimeEstimate() {
        return timeEstimate;
    }

    public void setTimeEstimate(Time timeEstimate) {
        this.timeEstimate = timeEstimate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRecurring() {
        return isRecurring;
    }

    public void setRecurring(Boolean recurring) {
        isRecurring = recurring;
    }

    public RecurrenceInterval getRecurranceInterval() {
        return recurranceInterval;
    }

    public void setRecurranceInterval(RecurrenceInterval recurranceInterval) {
        this.recurranceInterval = recurranceInterval;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }
}
