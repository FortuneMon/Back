package FortuneMonBackEnd.fortuneMon.repository;

import FortuneMonBackEnd.fortuneMon.domain.UserFortune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFortuneRepository extends JpaRepository<UserFortune, Long> {
    List<UserFortune> findAllByUserId(Long userId);
}
