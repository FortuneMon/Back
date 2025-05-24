package FortuneMonBackEnd.fortuneMon.DTO;

import lombok.*;

import java.time.LocalDateTime;

public class UserResponseDTO {
    @AllArgsConstructor
    @RequiredArgsConstructor
    @Getter
    @Builder
    public static class UserSignUpResponseDTO {
        private Long userId;
        private String nickname;
        private LocalDateTime createdAt;
    }
}
