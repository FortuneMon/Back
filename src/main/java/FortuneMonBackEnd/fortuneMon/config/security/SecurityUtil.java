package FortuneMonBackEnd.fortuneMon.config.security;

import FortuneMonBackEnd.fortuneMon.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {
    public static Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof User user) {
            return user.getId();
        }

        throw new IllegalStateException("로그인 정보가 존재하지 않습니다.");
    }
}
