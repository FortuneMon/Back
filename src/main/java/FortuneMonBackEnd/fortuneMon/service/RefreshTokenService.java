package FortuneMonBackEnd.fortuneMon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RedisTemplate<String, String> redisTemplate;

    public void saveRefreshToken(String loginId, String refreshToken, long expirationMillis) {
        redisTemplate.opsForValue().set(loginId, refreshToken, Duration.ofMillis(expirationMillis));
    }

    public String getRefreshToken(String loginId) {
        return redisTemplate.opsForValue().get(loginId);
    }

    public void deleteRefreshToken(String loginId) {
        redisTemplate.delete(loginId);
    }
}
