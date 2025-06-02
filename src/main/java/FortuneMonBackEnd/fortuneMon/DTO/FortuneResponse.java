package FortuneMonBackEnd.fortuneMon.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FortuneResponse {
    private LocalDate date;
    private String category;
    private String content;
}
