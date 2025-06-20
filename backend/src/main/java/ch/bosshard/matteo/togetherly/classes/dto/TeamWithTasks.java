package ch.bosshard.matteo.togetherly.classes.dto;

import ch.bosshard.matteo.togetherly.classes.entity.Task;
import ch.bosshard.matteo.togetherly.classes.entity.Team;

import java.util.List;

public class TeamWithTasks {
    private Team team;
    private List<Task> tasks;

    public TeamWithTasks(Team team, List<Task> tasks) {
        this.team = team;
        this.tasks = tasks;
    }

    public Team getTeam() {
        return team;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}