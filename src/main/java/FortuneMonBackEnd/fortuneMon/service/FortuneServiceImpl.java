package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.FortuneResponse;
import FortuneMonBackEnd.fortuneMon.config.security.SecurityUtil;
import FortuneMonBackEnd.fortuneMon.domain.UserFortune;
import FortuneMonBackEnd.fortuneMon.repository.UserFortuneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FortuneServiceImpl implements FortuneService{

    private final UserFortuneRepository userFortuneRepository;

    public FortuneResponse todayFortune(LocalDate today){
        Long userId = SecurityUtil.getCurrentUserId();
        List<UserFortune> userFortunes = userFortuneRepository.findAllByUserId(userId);

        for(UserFortune userFortune : userFortunes){
            if(userFortune.getDate().equals(today)){
                return new FortuneResponse(userFortune.getDate(), userFortune.getAdvice());
            }
        }
        // 오늘의 운세가 아직 없음
        return null;
    }
}
