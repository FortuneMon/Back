package FortuneMonBackEnd.fortuneMon.repository;

import FortuneMonBackEnd.fortuneMon.DTO.UserRoutineInfoResponseDTO;
import FortuneMonBackEnd.fortuneMon.DTO.UserRoutineResponse;
import FortuneMonBackEnd.fortuneMon.domain.RoutineLog;
import FortuneMonBackEnd.fortuneMon.domain.UserRoutine;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoutineRepository extends JpaRepository<UserRoutine, Long> {

    @Query("SELECT new FortuneMonBackEnd.fortuneMon.DTO.UserRoutineInfoResponseDTO( " +
            "r.name, " +
            "COALESCE(rl.isCompleted, false), " +
            "ur.createdAt) " +
            "FROM UserRoutine ur " +
            "JOIN ur.routine r " +
            "LEFT JOIN RoutineLog rl ON rl.userRoutine = ur AND rl.date = :today " +
            "WHERE ur.user.id = :userId")
    List<UserRoutineInfoResponseDTO> findUserRoutinesWithLog(@Param("userId") Long userId, @Param("today") LocalDate today);

    Optional<UserRoutine> findByUserIdAndRoutineId(Long userId, Long routineId);

    List<UserRoutine> findAllByUserId(Long userId);
}
