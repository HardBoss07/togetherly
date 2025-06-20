package ch.bosshard.matteo.togetherly.controllers;

import ch.bosshard.matteo.togetherly.classes.dto.TeamWithTasks;
import ch.bosshard.matteo.togetherly.classes.entity.Task;
import ch.bosshard.matteo.togetherly.classes.entity.Team;
import ch.bosshard.matteo.togetherly.classes.repository.TaskRepository;
import ch.bosshard.matteo.togetherly.classes.repository.TeamMembershipRepository;
import ch.bosshard.matteo.togetherly.classes.repository.TeamRepository;
import ch.bosshard.matteo.togetherly.classes.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamRepository teamRepository;
    private final TeamMembershipRepository teamMembershipRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public TeamController(final TeamRepository teamRepository, final TeamMembershipRepository teamMembershipRepository, final UserRepository userRepository, TaskRepository taskRepository) {
        this.teamRepository = teamRepository;
        this.teamMembershipRepository = teamMembershipRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("{teamname}")
    public ResponseEntity<List<Task>> getTeamTasks(@PathVariable("teamname") String teamname) {
        // get ID from teamname
        var team = teamRepository.findByName(teamname).orElseThrow(() -> new RuntimeException("Team not found: " + teamname));

        // get all tasks that belong to a team
        var tasks = taskRepository.findByTeamId(team.getId());

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/personal")
    public ResponseEntity<List<Team>> getUserTeams(Authentication authentication) {
        String username = authentication.getName();

        // get ID from username
        var user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found: " + username));

        // get all memberships for the user
        var memberships = teamMembershipRepository.findByUserId(user.getId());

        // map to team IDs
        var teamIds = memberships.stream().map(m -> m.getTeam().getId()).toList();

        // fetch team objects
        var teams = teamRepository.findAllById(teamIds);

        return ResponseEntity.ok(teams);
    }

    @GetMapping("/full/{teamname}")
    public ResponseEntity<TeamWithTasks> getTeamWithTasks(@PathVariable("teamname") String teamname) {
        var team = teamRepository.findByName(teamname)
                .orElseThrow(() -> new RuntimeException("Team not found: " + teamname));

        var tasks = taskRepository.findByTeamId(team.getId());

        return ResponseEntity.ok(new TeamWithTasks(team, tasks));
    }
}
