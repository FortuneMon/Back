package FortuneMonBackEnd.fortuneMon.repository;

import FortuneMonBackEnd.fortuneMon.domain.Fortune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FortuneRepository extends JpaRepository<Fortune, Long> {
    List<Fortune> findAllByBallId(Long ballId);
}
