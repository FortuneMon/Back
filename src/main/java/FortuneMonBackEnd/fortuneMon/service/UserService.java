package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.UserRequestDTO;
import FortuneMonBackEnd.fortuneMon.DTO.UserResponseDTO;
import jakarta.validation.Valid;

public interface UserService {
    UserResponseDTO.SignUpResponseDTO signUp(UserRequestDTO.@Valid SignUpRequestDTO request);

    UserResponseDTO.SignInResponseDTO signIn(UserRequestDTO.@Valid SignInRequestDTO request);

    UserResponseDTO.RefreshTokenResponseDTO refresh(String authHeader, String refreshToken);
}
