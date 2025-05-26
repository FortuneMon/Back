package FortuneMonBackEnd.fortuneMon.repository;

import FortuneMonBackEnd.fortuneMon.domain.RoutineLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoutineLogRepository extends JpaRepository<RoutineLog, Long> {
    Optional<RoutineLog> findByUserRoutineIdAndDate(Long id, LocalDate date);

    List<RoutineLog> findByUserRoutineIdInAndDateBetween(List<Long> userRoutineIds, LocalDate startOfMonth, LocalDate endOfMonth);
}
