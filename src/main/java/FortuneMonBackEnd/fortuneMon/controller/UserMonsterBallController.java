package FortuneMonBackEnd.fortuneMon.controller;

import FortuneMonBackEnd.fortuneMon.DTO.UserMonsterBallResponseDTO;
import FortuneMonBackEnd.fortuneMon.config.security.SecurityUtil;
import FortuneMonBackEnd.fortuneMon.service.UserMonsterBallService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users/balls")
@RequiredArgsConstructor
public class UserMonsterBallController {
    private final UserMonsterBallService userMonsterBallService;

    @GetMapping("")
    public List<UserMonsterBallResponseDTO> getUserMonsterBall(){
        Long userId = SecurityUtil.getCurrentUserId();

        return userMonsterBallService.getUserMonsterBall(userId);
    }
}
