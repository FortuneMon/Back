package FortuneMonBackEnd.fortuneMon.controller;

import FortuneMonBackEnd.fortuneMon.DTO.FortuneResponse;
import FortuneMonBackEnd.fortuneMon.apiPayload.ApiResponse;
import FortuneMonBackEnd.fortuneMon.service.FortuneService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class FortuneController {
    private final FortuneService fortuneService;

    @GetMapping("/users/fortune")
    public ApiResponse<?> getTodayFortune(){
        LocalDate today = LocalDate.now();
        FortuneResponse response = fortuneService.todayFortune(today);

        return ApiResponse.onSuccess(response);
    }
}
