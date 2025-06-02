package FortuneMonBackEnd.fortuneMon.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest{
    private String love;
    private String health;
    private String wealth;
}