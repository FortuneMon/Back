package FortuneMonBackEnd.fortuneMon.repository;

import FortuneMonBackEnd.fortuneMon.domain.UserPokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserPokemonRepository extends JpaRepository<UserPokemon, Long> {
    // 특정 유저의 포켓몬 가져오기
    List<UserPokemon> findByUserId(Long userId);
}
