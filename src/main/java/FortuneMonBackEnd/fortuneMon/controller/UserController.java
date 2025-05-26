package FortuneMonBackEnd.fortuneMon.controller;

import FortuneMonBackEnd.fortuneMon.DTO.UserRequestDTO;
import FortuneMonBackEnd.fortuneMon.DTO.UserResponseDTO;
import FortuneMonBackEnd.fortuneMon.DTO.UserRoutineResponse;
import FortuneMonBackEnd.fortuneMon.apiPayload.ApiResponse;
import FortuneMonBackEnd.fortuneMon.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

}
