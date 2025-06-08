package ch.bosshard.matteo.togetherly.classes.repository;

import ch.bosshard.matteo.togetherly.classes.entity.team_membership.TeamMembership;
import ch.bosshard.matteo.togetherly.classes.entity.team_membership.TeamMembershipId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMembershipRepository extends JpaRepository<TeamMembership, TeamMembershipId> {
    List<TeamMembership> findByUserId (Long userId); // get all teams a user is in
    List<TeamMembership> findByTeamId (Long teamId); // get all users in a team

}
