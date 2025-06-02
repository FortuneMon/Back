package FortuneMonBackEnd.fortuneMon.domain;

import FortuneMonBackEnd.fortuneMon.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
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

    @OneToMany(mappedBy = "monsterBall")
    private List<Fortune> fortune;
}

