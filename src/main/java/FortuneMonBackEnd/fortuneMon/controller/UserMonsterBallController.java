package FortuneMonBackEnd.fortuneMon.controller;

import FortuneMonBackEnd.fortuneMon.DTO.UserMonsterBallResponseDTO;
import FortuneMonBackEnd.fortuneMon.DTO.UserPokemonDTO;
import FortuneMonBackEnd.fortuneMon.config.security.SecurityUtil;
import FortuneMonBackEnd.fortuneMon.service.UserMonsterBallService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{id}/open")
    public UserPokemonDTO openMonsterBall(@PathVariable("id") Long ballId){
        return userMonsterBallService.openMonsterBall(ballId);
    }
}
