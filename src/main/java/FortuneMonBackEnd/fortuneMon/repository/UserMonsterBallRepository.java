package FortuneMonBackEnd.fortuneMon.repository;

import FortuneMonBackEnd.fortuneMon.domain.UserBall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMonsterBallRepository extends JpaRepository<UserBall, Long> {
    List<UserBall> findByUserId(Long userId);
}
