package ch.bosshard.matteo.togetherly.classes.repository;


import ch.bosshard.matteo.togetherly.classes.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
