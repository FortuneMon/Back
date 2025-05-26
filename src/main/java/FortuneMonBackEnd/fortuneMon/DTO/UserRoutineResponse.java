package FortuneMonBackEnd.fortuneMon.DTO;

import FortuneMonBackEnd.fortuneMon.domain.UserRoutine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoutineResponse {
    private String nickname;
    private List<UserRoutineInfoResponseDTO> routines;
}

