package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.FortuneResponse;
import FortuneMonBackEnd.fortuneMon.config.security.SecurityUtil;
import FortuneMonBackEnd.fortuneMon.domain.*;
import FortuneMonBackEnd.fortuneMon.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FortuneServiceImpl implements FortuneService{

    private final UserFortuneRepository userFortuneRepository;
    private final UserRepository userRepository;
    private final MonsterBallRepository monsterBallRepository;
    private final UserMonsterBallRepository userMonsterBallRepository;
    private final FortuneRepository fortuneRepository;

    @Override
    public List<FortuneResponse> todayFortune(LocalDate today){
        Long userId = SecurityUtil.getCurrentUserId();
        List<UserFortune> userFortunes = userFortuneRepository.findAllByUserId(userId);
        List<FortuneResponse> fortuneResponses = null;

        for(UserFortune userFortune : userFortunes){
            if(userFortune.getDate().equals(today)){
                fortuneResponses.add(new FortuneResponse(userFortune.getDate(), fortuneRepository.findById(userFortune.getFortune().getId()).get().getContent()));
            }
        }
        return fortuneResponses;
    }

    @Override
    public List<FortuneResponse> drawFortune(String love, String health, String wealth) {
        Long ball;
        Long userId = SecurityUtil.getCurrentUserId();
        Optional<User> user = userRepository.findById(userId);
        Optional<MonsterBall> monsterBall;
        double rand = Math.random();
        if(rand < 0.1){
            monsterBall = monsterBallRepository.findById(1L); // 몬스터볼
        } else if (rand < 0.5) {
            monsterBall = monsterBallRepository.findById(2L); // 슈퍼볼
        } else if (rand < 0.85) {
            monsterBall = monsterBallRepository.findById(3L); // 하이퍼볼
        }else {
            monsterBall = monsterBallRepository.findById(4L); // 마스터볼
        }

        UserBall userBall = new UserBall();
        userBall.setUser(user.get());
        userBall.setMonsterBall(monsterBall.get());
        userBall.setOpen(false);
        userMonsterBallRepository.save(userBall); // 뽑은 볼 저장

        // 볼 별 운세를 뽑은 후 카테고리별 운세로 나눠서 저장
        List<Fortune> fortunes = fortuneRepository.findAllByMonsterBallId(userBall.getMonsterBall().getId());
        List<Fortune> loveFortunes = fortunes.stream()
                .filter(fortune -> love.equals(fortune.getCategory()))
                .toList();
        List<Fortune> healthFortunes = fortunes.stream()
                .filter(fortune -> health.equals(fortune.getCategory()))
                .toList();
        List<Fortune> wealthFortunes = fortunes.stream()
                .filter(fortune -> wealth.equals(fortune.getCategory()))
                .toList();

        // 카테고리별 운세 랜덤 뽑기
        Random random = new Random();
        Fortune loveFortune = loveFortunes.get(random.nextInt(loveFortunes.size()));
        Fortune healthFortune = healthFortunes.get(random.nextInt(healthFortunes.size()));
        Fortune wealthFortune = wealthFortunes.get(random.nextInt(wealthFortunes.size()));

        // 카테고리별 운세 유저 운세에 저장
        UserFortune userLoveFortune = new UserFortune();
        userLoveFortune.setFortune(loveFortune);
        userLoveFortune.setUser(user.get());
        userLoveFortune.setAdvice("gpt"); // gpt 연동 필요
        userLoveFortune.setDate(LocalDate.now());
        userFortuneRepository.save(userLoveFortune);

        UserFortune userHealthFortune = new UserFortune();
        userHealthFortune.setFortune(healthFortune);
        userHealthFortune.setUser(user.get());
        userHealthFortune.setAdvice("gpt"); // gpt 연동 필요
        userHealthFortune.setDate(LocalDate.now());
        userFortuneRepository.save(userHealthFortune);

        UserFortune userWealthFortune = new UserFortune();
        userWealthFortune.setFortune(wealthFortune);
        userWealthFortune.setUser(user.get());
        userWealthFortune.setAdvice("gpt"); // gpt 연동 필요
        userWealthFortune.setDate(LocalDate.now());
        userFortuneRepository.save(userWealthFortune);

        List<FortuneResponse> fortuneResponses = null;
        fortuneResponses.add(new FortuneResponse(LocalDate.now(), loveFortune.getContent()));
        fortuneResponses.add(new FortuneResponse(LocalDate.now(), healthFortune.getContent()));
        fortuneResponses.add(new FortuneResponse(LocalDate.now(), wealthFortune.getContent()));

        return fortuneResponses;
    }
}
