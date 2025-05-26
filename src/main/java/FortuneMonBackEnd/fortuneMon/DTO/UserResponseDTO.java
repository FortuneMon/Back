package FortuneMonBackEnd.fortuneMon.DTO;

import lombok.*;

import java.time.LocalDateTime;

public class UserResponseDTO {
    @AllArgsConstructor
    @RequiredArgsConstructor
    @Getter
    @Builder
    public static class SignUpResponseDTO {
        private Long userId;
        private String nickname;
        private LocalDateTime createdAt;
    }

    @AllArgsConstructor
    @RequiredArgsConstructor
    @Getter
    @Builder
    public static class SignInResponseDTO {
        private String accessToken;
        private String refreshToken;
        private String nickname;
        private LocalDateTime createdAt;
    }

    @AllArgsConstructor
    @RequiredArgsConstructor
    @Getter
    @Builder
    public static class RefreshTokenResponseDTO {
        private String newAccessToken;
        private String nickname;
    }

    @AllArgsConstructor
    @RequiredArgsConstructor
    @Getter
    @Builder
    public static class UsersRoutineDTO {
        private String nickName;

    }
}
