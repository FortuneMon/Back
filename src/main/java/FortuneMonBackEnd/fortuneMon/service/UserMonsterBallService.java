package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.UserMonsterBallResponseDTO;
import FortuneMonBackEnd.fortuneMon.DTO.UserPokemonDTO;

import java.util.List;

public interface UserMonsterBallService {
    //유저의 몬스터볼을 조회
    List<UserMonsterBallResponseDTO> getUserMonsterBall(Long userId);

    //몬스터볼을 뽑고 포켓몬을 도감에 저장
    UserPokemonDTO openMonsterBall(Long ballId);
}
