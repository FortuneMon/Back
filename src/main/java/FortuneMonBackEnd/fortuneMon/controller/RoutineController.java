package FortuneMonBackEnd.fortuneMon.controller;

import FortuneMonBackEnd.fortuneMon.DTO.RoutineResponse;
import FortuneMonBackEnd.fortuneMon.apiPayload.ApiResponse;
import FortuneMonBackEnd.fortuneMon.service.RoutineService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/routines")
@RequiredArgsConstructor
public class RoutineController {

    private final RoutineService routineService;


    @Operation(summary = "카테고리 별 루틴 조회", description =
            "# 카테고리 별 루틴 조회 API 입니다. 카테고리 이름을 입력해주세요."
    )
    @GetMapping("")
    public ApiResponse<?> getRoutines(@RequestParam String category) {

        List<RoutineResponse> response = routineService.getRoutines(category);

        return ApiResponse.onSuccess(response);
    }
}
