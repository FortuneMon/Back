package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.FortuneResponse;

import java.time.LocalDate;

public interface FortuneService {
    FortuneResponse todayFortune(LocalDate today);
}
