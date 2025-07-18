package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.FortuneResponse;
import FortuneMonBackEnd.fortuneMon.config.security.SecurityUtil;
import FortuneMonBackEnd.fortuneMon.domain.*;
import FortuneMonBackEnd.fortuneMon.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private final UserRoutineRepository userRoutineRepository;
    private final GPTService gptService;

    @Override
    public List<FortuneResponse> todayFortune(LocalDate today){
        Long userId = SecurityUtil.getCurrentUserId();
        List<UserFortune> userFortunes = userFortuneRepository.findAllByUserId(userId);
        List<FortuneResponse> fortuneResponses = new ArrayList<>();

        for(UserFortune userFortune : userFortunes){
            if(userFortune.getDate().equals(today)){
                fortuneResponses.add(new FortuneResponse(userFortune.getDate(), userFortune.getFortune().getCategory(), fortuneRepository.findById(userFortune.getFortune().getId()).get().getContent(), userFortune.getAdvice()));
            }
        }
        return fortuneResponses;
    }

    @Override
    public List<FortuneResponse> drawFortune() {
        Long ball;
        Long userId = SecurityUtil.getCurrentUserId();
        Optional<User> user = userRepository.findById(userId);
        Optional<MonsterBall> monsterBall;
        List<UserRoutine> userRoutines = userRoutineRepository.findAllByUserId(userId);
        boolean loveRoutine = false, healthRoutine = false, wealthRoutine = false, growRoutine = false;
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
                .filter(fortune -> "관계".equals(fortune.getCategory()))
                .toList();
        List<Fortune> healthFortunes = fortunes.stream()
                .filter(fortune -> "건강".equals(fortune.getCategory()))
                .toList();
        List<Fortune> wealthFortunes = fortunes.stream()
                .filter(fortune -> "재물".equals(fortune.getCategory()))
                .toList();
        List<Fortune> growFortunes = fortunes.stream()
                .filter(fortune -> "자기계발".equals(fortune.getCategory()))
                .toList();

        // 카테고리별 운세 랜덤 뽑기
        Random random = new Random();
        Fortune loveFortune = loveFortunes.get(random.nextInt(loveFortunes.size()));
        Fortune healthFortune = healthFortunes.get(random.nextInt(healthFortunes.size()));
        Fortune wealthFortune = wealthFortunes.get(random.nextInt(wealthFortunes.size()));
        Fortune growFortune = growFortunes.get(random.nextInt(growFortunes.size()));

        UserFortune userLoveFortune = new UserFortune();
        UserFortune userHealthFortune = new UserFortune();
        UserFortune userWealthFortune = new UserFortune();
        UserFortune userGrowFortune = new UserFortune();
        // 유저 루틴의 카테고리별 운세 저장
        for(UserRoutine userRoutine : userRoutines){
            if(userRoutine.getRoutine().getCategory().equals("관계") && !loveRoutine){
                userLoveFortune.setFortune(loveFortune);
                userLoveFortune.setUser(user.get());
                userLoveFortune.setAdvice(gptService.requestGPT(loveFortune.getContent(), "관계"));
                userLoveFortune.setDate(LocalDate.now());
                userFortuneRepository.save(userLoveFortune);
                loveRoutine = true;
            } else if (userRoutine.getRoutine().getCategory().equals("건강") && !healthRoutine) {
                userHealthFortune.setFortune(healthFortune);
                userHealthFortune.setUser(user.get());
                userHealthFortune.setAdvice(gptService.requestGPT(healthFortune.getContent(), "건강"));
                userHealthFortune.setDate(LocalDate.now());
                userFortuneRepository.save(userHealthFortune);
                healthRoutine = true;
            } else if (userRoutine.getRoutine().getCategory().equals("재물") && !wealthRoutine) {
                userWealthFortune.setFortune(wealthFortune);
                userWealthFortune.setUser(user.get());
                userWealthFortune.setAdvice(gptService.requestGPT(wealthFortune.getContent(), "재물"));
                userWealthFortune.setDate(LocalDate.now());
                userFortuneRepository.save(userWealthFortune);
                wealthRoutine = true;
            } else if (userRoutine.getRoutine().getCategory().equals("자기계발") && !growRoutine) {
                userGrowFortune.setFortune(growFortune);
                userGrowFortune.setUser(user.get());
                userGrowFortune.setAdvice(gptService.requestGPT(wealthFortune.getContent(), "자기계발"));
                userGrowFortune.setDate(LocalDate.now());
                userFortuneRepository.save(userGrowFortune);
                growRoutine = true;
            }
        }
        // 루틴이 없는 경우 모든 카테고리별 운세 유저 운세에 저장
        if(!loveRoutine && !healthRoutine && !wealthRoutine && !growRoutine) {
            userLoveFortune.setFortune(loveFortune);
            userLoveFortune.setUser(user.get());
            userLoveFortune.setAdvice(null);
            userLoveFortune.setDate(LocalDate.now());
            userFortuneRepository.save(userLoveFortune);

            userHealthFortune.setFortune(healthFortune);
            userHealthFortune.setUser(user.get());
            userHealthFortune.setAdvice(null);
            userHealthFortune.setDate(LocalDate.now());
            userFortuneRepository.save(userHealthFortune);

            userWealthFortune.setFortune(wealthFortune);
            userWealthFortune.setUser(user.get());
            userWealthFortune.setAdvice(null);
            userWealthFortune.setDate(LocalDate.now());
            userFortuneRepository.save(userWealthFortune);

            userGrowFortune.setFortune(growFortune);
            userGrowFortune.setUser(user.get());
            userGrowFortune.setAdvice(null);
            userGrowFortune.setDate(LocalDate.now());
            userFortuneRepository.save(userGrowFortune);
        }

        List<FortuneResponse> fortuneResponses = new ArrayList<>();
        if(loveRoutine) {
            fortuneResponses.add(new FortuneResponse(LocalDate.now(), loveFortune.getCategory(), loveFortune.getContent(), userLoveFortune.getAdvice()));
        }
        if(healthRoutine) {
            fortuneResponses.add(new FortuneResponse(LocalDate.now(), healthFortune.getCategory(), healthFortune.getContent(), userHealthFortune.getAdvice()));
        }
        if(wealthRoutine) {
            fortuneResponses.add(new FortuneResponse(LocalDate.now(), wealthFortune.getCategory(), wealthFortune.getContent(), userWealthFortune.getAdvice()));
        }
        if(growRoutine){
            fortuneResponses.add(new FortuneResponse(LocalDate.now(), growFortune.getCategory(), growFortune.getContent(), userGrowFortune.getAdvice()));
        }
        if(!loveRoutine && !healthRoutine && !wealthRoutine && !growRoutine){
            fortuneResponses.add(new FortuneResponse(LocalDate.now(), loveFortune.getCategory(), loveFortune.getContent(), userLoveFortune.getAdvice()));
            fortuneResponses.add(new FortuneResponse(LocalDate.now(), healthFortune.getCategory(), healthFortune.getContent(), userHealthFortune.getAdvice()));
            fortuneResponses.add(new FortuneResponse(LocalDate.now(), wealthFortune.getCategory(), wealthFortune.getContent(), userWealthFortune.getAdvice()));
            fortuneResponses.add(new FortuneResponse(LocalDate.now(), growFortune.getCategory(), growFortune.getContent(), userGrowFortune.getAdvice()));
        }
        return fortuneResponses;
    }
}
