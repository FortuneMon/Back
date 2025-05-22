package FortuneMonBackEnd.fortuneMon.domain;

import FortuneMonBackEnd.fortuneMon.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;



@Entity
@Getter
@Setter
public class RoutineLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userRoutine_id")
    private UserRoutine userRoutine;

    private LocalDate date;

    private Boolean isCompleted;

}
