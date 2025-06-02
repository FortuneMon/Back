package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.RoutineResponse;
import FortuneMonBackEnd.fortuneMon.apiPayload.code.status.ErrorStatus;
import FortuneMonBackEnd.fortuneMon.apiPayload.exception.GeneralException;
import FortuneMonBackEnd.fortuneMon.config.security.SecurityUtil;
import FortuneMonBackEnd.fortuneMon.domain.Routine;
import FortuneMonBackEnd.fortuneMon.domain.User;
import FortuneMonBackEnd.fortuneMon.domain.UserRoutine;
import FortuneMonBackEnd.fortuneMon.repository.RoutineRepository;
import FortuneMonBackEnd.fortuneMon.repository.UserRepository;
import FortuneMonBackEnd.fortuneMon.repository.UserRoutineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoutineServiceImpl implements RoutineService {

    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;
    private final UserRoutineRepository userRoutineRepository;

    @Override
    public List<RoutineResponse> getRoutines(String category) {

        Long userId = SecurityUtil.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        // 유저가 선택한 루틴 목록
        List<UserRoutine> userRoutines = userRoutineRepository.findAllByUserId(userId);
        Set<Long> selectedRoutineIds = userRoutines.stream()
                .map(userRoutine -> userRoutine.getRoutine().getId())
                .collect(Collectors.toSet());

        // 카테고리 기반 전체 루틴
        List<Routine> routines = routineRepository.findAllByCategory(category);

        // Response 생성 (is_selected 포함)
        List<RoutineResponse> responses = routines.stream()
                .map(routine -> RoutineResponse.builder()
                        .id(routine.getId())
                        .name(routine.getName())
                        .is_selected(selectedRoutineIds.contains(routine.getId()))
                        .build())
                .collect(Collectors.toList());

        return responses;
    }
}
