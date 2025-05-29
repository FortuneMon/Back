package FortuneMonBackEnd.fortuneMon.repository;

import FortuneMonBackEnd.fortuneMon.domain.MonsterBall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonsterBallRepository extends JpaRepository<MonsterBall, Long> {
}
