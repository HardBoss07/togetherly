package ch.bosshard.matteo.togetherly.classes.entity.team_membership;

import ch.bosshard.matteo.togetherly.classes.entity.Team;
import ch.bosshard.matteo.togetherly.classes.entity.User;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "team_memberships")
@IdClass(TeamMembershipId.class)
public class TeamMembership {
    @Id
    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "team_id")
    private Team team;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "joined_at", insertable = false, updatable = false)
    private Timestamp joinedAt;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Timestamp joinedAt) {
        this.joinedAt = joinedAt;
    }
}
