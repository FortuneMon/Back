package FortuneMonBackEnd.fortuneMon.repository;

import FortuneMonBackEnd.fortuneMon.domain.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
}
