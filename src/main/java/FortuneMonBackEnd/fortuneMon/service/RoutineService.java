package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.RoutineResponse;

import java.util.List;

public interface RoutineService {
    List<RoutineResponse> getRoutines(String category);
}
