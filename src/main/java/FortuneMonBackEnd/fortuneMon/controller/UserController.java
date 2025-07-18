package FortuneMonBackEnd.fortuneMon.controller;

import FortuneMonBackEnd.fortuneMon.DTO.*;
import FortuneMonBackEnd.fortuneMon.apiPayload.ApiResponse;
import FortuneMonBackEnd.fortuneMon.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description =
            "# 회원가입 API 입니다. 닉네임과 아이디, 패스워드를 body에 입력해주세요."
    )
    @PostMapping("/signup")
    public ApiResponse<?> signup(@RequestBody @Valid UserRequestDTO.SignUpRequestDTO request) {
        UserResponseDTO.SignUpResponseDTO response = userService.signUp(request);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "로그인", description =
            "# 로그인 API 입니다. 아이디와 패스워드를 body에 입력해주세요."
    )
    @PostMapping("/signin")
    public ApiResponse<?> signin(@RequestBody @Valid UserRequestDTO.SignInRequestDTO request) {
        UserResponseDTO.SignInResponseDTO response = userService.signIn(request);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "Acccess Token 재발급", description =
            "# Acccess Token 재발급 API 입니다. "
    )
    @PostMapping("/refresh")
    public ApiResponse<?> refresh(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String refreshToken = request.getHeader("refreshToken");

        UserResponseDTO.RefreshTokenResponseDTO response = userService.refresh(authHeader, refreshToken);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "유저의 진행중인 루틴 조회", description =
            "# 유저의 진행중인 루틴 조회 API 입니다. 로그인 후 진행하세요. "
    )
    @GetMapping("/routine")
    public ApiResponse<?> getMyRoutines() {
        UserRoutineResponse response = userService.getMyRoutines();
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "유저의 진행중인 루틴 추가", description =
            "# 유저의 진행중인 루틴 추가 API 입니다. 로그인 후 진행하세요. "
    )
    @PostMapping("/routines/{id}")
    public ApiResponse<?> setMyRoutines(@PathVariable("id") Long routineId) {
        UserResponseDTO.UsersRoutineDTO response = userService.setMyRoutines(routineId);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "유저의 진행중인 루틴 삭제", description =
            "# 유저의 진행중인 루틴 삭제 API 입니다. 로그인 후 진행하세요. "
    )
    @DeleteMapping("/routines/{id}")
    public ApiResponse<?> deleteMyRoutines(@PathVariable("id") Long routineId) {
        UserResponseDTO.UsersRoutineDTO response = userService.deleteMyRoutines(routineId);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "유저의 진행중인 루틴 수행여부 수정", description =
            "# 유저의 진행중인 루틴 삭제 API 입니다. 로그인 후 진행하세요. "
    )
    @PatchMapping("/routines/{id}/status")
    public ApiResponse<?> setMyRoutineStatus(@PathVariable("id") Long routineId) {
        RoutineLogResponse response = userService.setMyRoutineStatus(routineId);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "유저의 루틴 수행 관련 통계 조회", description =
            "# 유저의 루틴 수행 관련 통계 조회 API 입니다. 로그인 후 진행하세요. "
    )
    @GetMapping("/routines/{date}/statistics")
    public ApiResponse<?> getMyRoutineStatistics(@PathVariable LocalDate date) {
        RoutineStatisticsResponse response = userService.getMyRoutinesStatistics(date);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "유저 로그인 id 중복 체크", description =
            "# 유저 로그인 id 중복 체크 API 입니다. "
    )
    @GetMapping("/check-login-id")
    public ApiResponse<?> checkLoginIdDuplicate(@RequestParam String loginId) {
        UserResponseDTO.UsersDuplicateCheckDTO response = userService.isLoginIdDuplicate(loginId);
        return ApiResponse.onSuccess(response);
    }


    @Operation(summary = "유저 닉네임 중복 체크", description =
            "# 유저 닉네임 중복 체크 API 입니다."
    )
    @GetMapping("/check-nickname")
    public ApiResponse<?> checkNicknameDuplicate(@RequestParam String nickname) {
        UserResponseDTO.UsersDuplicateCheckDTO response = userService.isNicknameDuplicate(nickname);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "유저의 정보 조회(닉네임, 파트너 포켓몬)", description =
            "# 유저의 정보 조회(닉네임, 파트너 포켓몬) API 입니다. 로그인 후 진행하세요. "
    )
    @GetMapping("/info")
    public ApiResponse<?> getMyInfo() {
        UserResponseDTO.UsersInfoDTO response = userService.getMyInfo();
        return ApiResponse.onSuccess(response);
    }


}
