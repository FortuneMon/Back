package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.UserRequestDTO;
import FortuneMonBackEnd.fortuneMon.DTO.UserResponseDTO;

public interface UserService {
    UserResponseDTO.UserSignUpResponseDTO signup(UserRequestDTO.SignUpRequestDTO request);
}
