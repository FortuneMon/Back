package FortuneMonBackEnd.fortuneMon.domain;

import FortuneMonBackEnd.fortuneMon.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PokemonBallRate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pokemonId;   // 연관관계 없음

    private Long ballId;      // 연관관계 없음

    private Float probability;
}
