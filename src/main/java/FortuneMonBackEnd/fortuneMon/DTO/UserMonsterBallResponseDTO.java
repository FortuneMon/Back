package FortuneMonBackEnd.fortuneMon.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserMonsterBallResponseDTO {
    private Long ballId;
    private String url;
    private LocalDateTime created_at;
    private boolean isOpen;
}
