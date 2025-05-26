package FortuneMonBackEnd.fortuneMon.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoutineStatisticsResponse {
    private String nickName;
    private List<statisticsResponse> statistics;


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class statisticsResponse {
        private String routineName;
        private Map<LocalDate, Boolean> daysStatistics;
    }
}
