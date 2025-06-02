package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.FortuneResponse;

import java.time.LocalDate;
import java.util.List;

public interface FortuneService {
    List<FortuneResponse> todayFortune(LocalDate today);

    List<FortuneResponse> drawFortune(String love, String health, String wealth);
}
