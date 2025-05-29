package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.UserMonsterBallResponseDTO;

import java.util.List;

public interface UserMonsterBallService {
    List<UserMonsterBallResponseDTO> getUserMonsterBall(Long userId);
}
