package FortuneMonBackEnd.fortuneMon.domain;

import FortuneMonBackEnd.fortuneMon.domain.common.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class MonsterBall extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String ballName;

    @Column(nullable = false, length = 255)
    private String url;

    @OneToMany(mappedBy = "monsterBall")
    private List<UserBall> userBall;

    @OneToOne(mappedBy = "monsterBall")
    private Fortune fortune;
}

