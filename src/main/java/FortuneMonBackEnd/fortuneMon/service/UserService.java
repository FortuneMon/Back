package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.UserRequestDTO;
import FortuneMonBackEnd.fortuneMon.DTO.UserResponseDTO;
import FortuneMonBackEnd.fortuneMon.DTO.UserRoutineResponse;
import jakarta.validation.Valid;

public interface UserService {
    UserResponseDTO.SignUpResponseDTO signUp(UserRequestDTO.@Valid SignUpRequestDTO request);

    UserResponseDTO.SignInResponseDTO signIn(UserRequestDTO.@Valid SignInRequestDTO request);

    UserResponseDTO.RefreshTokenResponseDTO refresh(String authHeader, String refreshToken);

    UserRoutineResponse getMyRoutines();

    UserResponseDTO.UsersRoutineDTO setMyRoutines(Long routineId);

    UserResponseDTO.UsersRoutineDTO deleteMyRoutines(Long routineId);
}
