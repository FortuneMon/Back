package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.UserRequestDTO;
import FortuneMonBackEnd.fortuneMon.DTO.UserResponseDTO;
import FortuneMonBackEnd.fortuneMon.DTO.UserRoutineInfoResponseDTO;
import FortuneMonBackEnd.fortuneMon.DTO.UserRoutineResponse;
import FortuneMonBackEnd.fortuneMon.apiPayload.code.status.ErrorStatus;
import FortuneMonBackEnd.fortuneMon.apiPayload.exception.GeneralException;
import FortuneMonBackEnd.fortuneMon.config.security.SecurityUtil;
import FortuneMonBackEnd.fortuneMon.domain.User;
import FortuneMonBackEnd.fortuneMon.jwt.JwtUtil;
import FortuneMonBackEnd.fortuneMon.repository.UserRepository;
import FortuneMonBackEnd.fortuneMon.repository.UserRoutineRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final UserRoutineRepository userRoutineRepository;

    @Override
    public UserResponseDTO.SignUpResponseDTO signUp(UserRequestDTO.SignUpRequestDTO request) {

        userRepository.findByLoginId(request.getLoginId())
                .ifPresent(user -> {
                    throw new GeneralException(ErrorStatus.ID_ALREADY_EXIST);
                });

        User user = User.builder()
                .loginId(request.getLoginId())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .build();

        userRepository.save(user);

        return UserResponseDTO.SignUpResponseDTO.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Override
    public UserResponseDTO.SignInResponseDTO signIn(UserRequestDTO.@Valid SignInRequestDTO request) {
        User user = userRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new GeneralException(ErrorStatus.PASSWORD_NOT_EQUAL);
        }

        String accessToken = jwtUtil.generateAccessToken(user.getLoginId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getLoginId());

        refreshTokenService.saveRefreshToken(user.getLoginId(), refreshToken, jwtUtil.getRefreshTokenExp());

        return UserResponseDTO.SignInResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .createdAt(user.getCreatedAt())
                .nickname(user.getNickname())
                .build();
    }

    @Override
    public UserResponseDTO.RefreshTokenResponseDTO refresh(String authHeader, String refreshToken) {
        if (authHeader == null || !authHeader.startsWith("Bearer ") || refreshToken == null) {
            throw new GeneralException(ErrorStatus.INVALID_TOKEN);
        }

        String expiredAccessToken = authHeader.substring(7);
        String loginId;
        System.out.println("expiredAccessToken = " + expiredAccessToken);

        try {
            loginId = jwtUtil.extractLoginIdIgnoreExpiration(expiredAccessToken);
        } catch (Exception e) {
            throw new GeneralException(ErrorStatus.INVALID_ACCESS_TOKEN);
        }

        String savedRefreshToken = refreshTokenService.getRefreshToken(loginId);

        if (savedRefreshToken == null || !savedRefreshToken.equals(refreshToken)) {
            throw new GeneralException(ErrorStatus.INVALID_REFRESH_TOKEN);
        }

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        return UserResponseDTO.RefreshTokenResponseDTO.builder()
                .newAccessToken(jwtUtil.generateAccessToken(loginId))
                .nickname(user.getNickname())
                .build();

    }

    @Override
    public UserRoutineResponse getMyRoutines() {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        List<UserRoutineInfoResponseDTO> routines =
                userRoutineRepository.findUserRoutinesWithLog(userId, LocalDate.now());

        return UserRoutineResponse.builder()
                .nickname(user.getNickname())
                .routines(routines)
                .build();
    }
}
