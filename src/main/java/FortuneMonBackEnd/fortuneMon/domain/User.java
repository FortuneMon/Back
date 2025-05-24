package FortuneMonBackEnd.fortuneMon.domain;

import FortuneMonBackEnd.fortuneMon.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String nickname;

    @Column(nullable = false, length = 20)
    private String loginId;

    @Column(nullable = false, length = 100)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<UserPokemon> userPokemon;

    @OneToMany(mappedBy = "user")
    private List<UserBall> userBall;

    @OneToMany(mappedBy = "user")
    private List<UserFortune> userFortune;

    @OneToMany(mappedBy = "user")
    private List<UserRoutine> userRoutine;
}

