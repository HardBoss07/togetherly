package ch.bosshard.matteo.togetherly.classes.repository;

import ch.bosshard.matteo.togetherly.classes.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByTeamId(Long teamId);

    List<Task> findByAssignedToId(Long userId);
}
