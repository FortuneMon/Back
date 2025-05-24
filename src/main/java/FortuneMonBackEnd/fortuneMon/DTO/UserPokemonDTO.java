package FortuneMonBackEnd.fortuneMon.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// 나의 포켓몬 목록을 조회할 때 소유 여부 컬럼을 추가하여 반환하기 위한 형식
@Getter
@Setter
public class UserPokemonDTO {
    private Long id;
    private String name;
    private String url;
    private String type;
    private String group;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    boolean isOwned; // 유저가 가지고 있는지 여부를 저장하기 위한 변수

    public UserPokemonDTO(Long id, String name, String url, String type, String group, boolean isOwned){
        this.id=id;
        this.name=name;
        this.url=url;
        this.type=type;
        this.group=group;
        this.isOwned=isOwned;
    }
}
