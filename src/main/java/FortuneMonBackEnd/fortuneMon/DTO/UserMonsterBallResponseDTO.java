package FortuneMonBackEnd.fortuneMon.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserMonsterBallResponseDTO {
    private String ballName;
    private String url;
    private Long count;
}
