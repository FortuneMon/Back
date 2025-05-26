package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.RoutineResponse;
import FortuneMonBackEnd.fortuneMon.domain.Routine;
import FortuneMonBackEnd.fortuneMon.repository.RoutineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoutineServiceImpl implements RoutineService {

    private final RoutineRepository routineRepository;

    @Override
    public List<RoutineResponse> getRoutines(String category) {
        List<Routine> routines = routineRepository.findAllByCategory(category);

        List<RoutineResponse> responses = routines.stream()
                .map(routine -> new RoutineResponse(
                        routine.getId(),
                        routine.getName()
                ))
                .collect(Collectors.toList());

        return responses;
    }
}
