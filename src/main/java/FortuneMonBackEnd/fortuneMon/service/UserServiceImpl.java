package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.*;
import FortuneMonBackEnd.fortuneMon.apiPayload.code.status.ErrorStatus;
import FortuneMonBackEnd.fortuneMon.apiPayload.exception.GeneralException;
import FortuneMonBackEnd.fortuneMon.config.security.SecurityUtil;
import FortuneMonBackEnd.fortuneMon.domain.Routine;
import FortuneMonBackEnd.fortuneMon.domain.RoutineLog;
import FortuneMonBackEnd.fortuneMon.domain.User;
import FortuneMonBackEnd.fortuneMon.domain.UserRoutine;
import FortuneMonBackEnd.fortuneMon.jwt.JwtUtil;
import FortuneMonBackEnd.fortuneMon.repository.RoutineLogRepository;
import FortuneMonBackEnd.fortuneMon.repository.RoutineRepository;
import FortuneMonBackEnd.fortuneMon.repository.UserRepository;
import FortuneMonBackEnd.fortuneMon.repository.UserRoutineRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final UserRoutineRepository userRoutineRepository;
    private final RoutineRepository routineRepository;
    private final RoutineLogRepository routineLogRepository;

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

    @Override
    public UserResponseDTO.UsersRoutineDTO setMyRoutines(Long routineId) {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(()-> new GeneralException(ErrorStatus.ROUTINE_NOT_FOUND));

        UserRoutine userRoutine = UserRoutine.builder()
                .user(user)
                .routine(routine)
                .routineLog(null)
                .build();

        RoutineLog routineLog = RoutineLog.builder()
                .userRoutine(userRoutine)
                .date(LocalDate.now())
                .isCompleted(false)
                .build();

        try {
            userRoutineRepository.save(userRoutine);
        } catch (DataIntegrityViolationException e) {
            throw new GeneralException(ErrorStatus.ROUTINE_ALREADY_EXIST);
        }

        routineLogRepository.save(routineLog);

        return UserResponseDTO.UsersRoutineDTO.builder()
                .nickName(user.getNickname())
                .routineName(routine.getName())
                .message(routine.getName() + " 추가 완료")
                .build();
    }

    @Override
    public UserResponseDTO.UsersRoutineDTO deleteMyRoutines(Long routineId) {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(()-> new GeneralException(ErrorStatus.ROUTINE_NOT_FOUND));

        UserRoutine userRoutine = userRoutineRepository.findByUserIdAndRoutineId(userId, routineId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ROUTINE_NOT_FOUND));

        userRoutineRepository.delete(userRoutine);

        return UserResponseDTO.UsersRoutineDTO.builder()
                .nickName(user.getNickname())
                .routineName(routine.getName())
                .message(routine.getName() + " 삭제 완료")
                .build();
    }

    @Override
    public RoutineLogResponse setMyRoutineStatus(Long routineId) {
        Long userId = SecurityUtil.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(()-> new GeneralException(ErrorStatus.ROUTINE_NOT_FOUND));

        UserRoutine userRoutine = userRoutineRepository.findByUserIdAndRoutineId(userId, routineId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ROUTINE_NOT_FOUND));

        RoutineLog routineLog = routineLogRepository.findByUserRoutineIdAndDate(userRoutine.getId(), LocalDate.now())
                .orElseThrow(()-> new GeneralException(ErrorStatus.ROUTINE_LOG_NOT_FOUND));

        routineLog.setIsCompleted(!routineLog.getIsCompleted());
        routineLogRepository.save(routineLog);

        return RoutineLogResponse.builder()
                .nickName(user.getNickname())
                .routineName(routine.getName())
                .isCompleted(routineLog.getIsCompleted())
                .build();
    }

    @Override
    public RoutineStatisticsResponse getMyRoutinesStatistics(LocalDate date) {
        Long userId = SecurityUtil.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        YearMonth month = YearMonth.from(date);
        LocalDate startOfMonth = month.atDay(1);
        LocalDate endOfMonth = month.atEndOfMonth();

        List<UserRoutine> userRoutines = userRoutineRepository.findAllByUserId(userId);

        List<Long> userRoutineIds = userRoutines.stream()
                .map(UserRoutine::getId)
                .toList();

        List<RoutineLog> logs = routineLogRepository.findByUserRoutineIdInAndDateBetween(userRoutineIds, startOfMonth, endOfMonth);

        Map<Long, Map<LocalDate, Boolean>> routineLogMap = logs.stream()
                .collect(Collectors.groupingBy(
                        log -> log.getUserRoutine().getId(),
                        Collectors.toMap(RoutineLog::getDate, RoutineLog::getIsCompleted)
                ));

        List<RoutineStatisticsResponse.statisticsResponse> statistics = userRoutines.stream()
                .map(ur -> RoutineStatisticsResponse.statisticsResponse.builder()
                        .routineName(ur.getRoutine().getName())
                        .daysStatistics(routineLogMap.getOrDefault(ur.getId(), new HashMap<>()))
                        .build())
                .toList();

        return RoutineStatisticsResponse.builder()
                .nickName(user.getNickname())
                .statistics(statistics)
                .build();

    }

    @Override
    public UserResponseDTO.UsersDuplicateCheckDTO isLoginIdDuplicate(String loginId) {
        boolean exists = userRepository.existsByLoginId(loginId);

        return UserResponseDTO.UsersDuplicateCheckDTO.builder()
                .is_confirmed(!exists)
                .message(exists ? "이미 사용 중인 로그인 ID입니다." : "사용 가능한 로그인 ID입니다.")
                .build();
    }

    @Override
    public UserResponseDTO.UsersDuplicateCheckDTO isNicknameDuplicate(String nickname) {
        boolean exists = userRepository.existsByNickname(nickname);

        return UserResponseDTO.UsersDuplicateCheckDTO.builder()
                .is_confirmed(!exists)
                .message(exists ? "이미 사용 중인 닉네임입니다." : "사용 가능한 닉네임입니다.")
                .build();
    }
}
