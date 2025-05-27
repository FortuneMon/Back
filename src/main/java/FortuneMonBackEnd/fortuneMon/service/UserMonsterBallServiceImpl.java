package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.UserMonsterBallResponseDTO;
import FortuneMonBackEnd.fortuneMon.domain.MonsterBall;
import FortuneMonBackEnd.fortuneMon.domain.UserBall;
import FortuneMonBackEnd.fortuneMon.repository.MonsterBallRepository;
import FortuneMonBackEnd.fortuneMon.repository.UserMonsterBallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMonsterBallServiceImpl implements UserMonsterBallService{
    private final UserMonsterBallRepository userMonsterBallRepository;
    private final MonsterBallRepository monsterBallRepository;

    @Override
    public List<UserMonsterBallResponseDTO> getUserMonsterBall(Long userId){
        List<UserBall> userBalls = userMonsterBallRepository.findByUserId(userId);

        // 이미 연 몬스터볼은 고려하지 않음
        userBalls.removeIf(UserBall::isOpen);

        return ballCount(userBalls);
    }

    // 각 몬스터볼의 개수를 세는 함수
    private List<UserMonsterBallResponseDTO> ballCount(List<UserBall> userBalls){
        Map<MonsterBall, Long> balls = userBalls.stream()
                .collect(Collectors.groupingBy(UserBall::getMonsterBall, Collectors.counting()));

        return balls.entrySet().stream()
                .map(ball -> new UserMonsterBallResponseDTO(
                        ball.getKey().getBallName(),
                        ball.getKey().getUrl(),
                        ball.getValue()))
                .collect(Collectors.toList());
    }
}
