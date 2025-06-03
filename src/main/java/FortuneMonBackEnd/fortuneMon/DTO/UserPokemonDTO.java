package FortuneMonBackEnd.fortuneMon.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

// 나의 포켓몬 목록을 조회할 때 소유 여부 컬럼을 추가하여 반환하기 위한 형식
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPokemonDTO {
    private Long id;
    private String name;
    private String url;
    private List<String> type;
    private String groupName;
    boolean isOwned; // 유저가 가지고 있는지 여부를 저장하기 위한 변수
}
