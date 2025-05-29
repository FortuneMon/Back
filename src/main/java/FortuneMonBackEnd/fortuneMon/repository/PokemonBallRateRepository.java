package FortuneMonBackEnd.fortuneMon.repository;

import FortuneMonBackEnd.fortuneMon.domain.PokemonBallRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokemonBallRateRepository extends JpaRepository<PokemonBallRate, Long> {
    List<PokemonBallRate> findAllByBallId(Long ballId);
}
