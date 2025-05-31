package FortuneMonBackEnd.fortuneMon.controller;

import FortuneMonBackEnd.fortuneMon.DTO.FortuneResponse;
import FortuneMonBackEnd.fortuneMon.apiPayload.ApiResponse;
import FortuneMonBackEnd.fortuneMon.service.FortuneService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FortuneController {
    private final FortuneService fortuneService;

    @Operation(summary = "오늘의 운세 조회", description =
            "# 오늘의 운세 조회 API입니다. 로그인 후 진행하세요.")
    @GetMapping("/users/fortune")
    public ApiResponse<?> getTodayFortune(){
        LocalDate today = LocalDate.now();
        List<FortuneResponse> response = fortuneService.todayFortune(today);

        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "오늘의 운세 뽑기", description =
            "# 오늘의 운세 뽑기 API입니다. 로그인 후 진행하세요.")
    @PostMapping("/fortunes")
    public ApiResponse<?> drawFortune(){
        List<FortuneResponse> response = fortuneService.drawFortune();

        return ApiResponse.onSuccess(response);
    }
}
