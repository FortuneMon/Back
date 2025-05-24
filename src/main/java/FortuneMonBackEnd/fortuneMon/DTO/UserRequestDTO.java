package FortuneMonBackEnd.fortuneMon.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequestDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignUpRequestDTO {
        @NotBlank(message = "아이디는 빈값일 수 없습니다.")
        private String loginId;

        @NotBlank(message = "비밀번호는 빈값일 수 없습니다.")
        private String password;

        @NotBlank(message = "닉네임은 빈값일 수 없습니다.")
        @Size(max = 8, message = "닉네임은 1 ~ 8자이어야 합니다.")
        private String nickname;
    }
}
