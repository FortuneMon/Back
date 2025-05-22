package FortuneMonBackEnd.fortuneMon.domain;

import FortuneMonBackEnd.fortuneMon.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Fortune extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ball_id", unique = true)
    private MonsterBall monsterBall;

    @Column(nullable = false, length = 20)
    private String category;

    @Column(nullable = false, length = 100)
    private String content;

    @Column(nullable = false, length = 20)
    private String fortuneType;

}
