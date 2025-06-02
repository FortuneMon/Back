package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.*;
import jakarta.validation.Valid;

import java.time.LocalDate;

public interface UserService {
    UserResponseDTO.SignUpResponseDTO signUp(UserRequestDTO.@Valid SignUpRequestDTO request);

    UserResponseDTO.SignInResponseDTO signIn(UserRequestDTO.@Valid SignInRequestDTO request);

    UserResponseDTO.RefreshTokenResponseDTO refresh(String authHeader, String refreshToken);

    UserRoutineResponse getMyRoutines();

    UserResponseDTO.UsersRoutineDTO setMyRoutines(Long routineId);

    UserResponseDTO.UsersRoutineDTO deleteMyRoutines(Long routineId);

    RoutineLogResponse setMyRoutineStatus(Long routineId);

    RoutineStatisticsResponse getMyRoutinesStatistics(LocalDate date);

    UserResponseDTO.UsersDuplicateCheckDTO isLoginIdDuplicate(String loginId);

    UserResponseDTO.UsersDuplicateCheckDTO isNicknameDuplicate(String nickname);

    UserResponseDTO.UsersInfoDTO getMyInfo();
}
