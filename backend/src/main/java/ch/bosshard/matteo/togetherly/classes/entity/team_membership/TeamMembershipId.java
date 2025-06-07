package ch.bosshard.matteo.togetherly.classes.entity.team_membership;

import java.io.Serializable;
import java.util.Objects;

public class TeamMembershipId implements Serializable {
    private Long team;
    private Long user;

    public TeamMembershipId() {}

    public TeamMembershipId(Long team, Long user) {
        this.team = team;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamMembershipId)) return false;
        TeamMembershipId that = (TeamMembershipId) o;
        return Objects.equals(team, that.team) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, user);
    }
}
