package FortuneMonBackEnd.fortuneMon.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoutineInfoResponseDTO {
        private Long routineId;
        private String name;
        private Boolean isCompleted;
        private LocalDateTime createdAt;
}
