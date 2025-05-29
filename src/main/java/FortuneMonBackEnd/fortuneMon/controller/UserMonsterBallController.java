package FortuneMonBackEnd.fortuneMon.controller;

import FortuneMonBackEnd.fortuneMon.DTO.UserMonsterBallResponseDTO;
import FortuneMonBackEnd.fortuneMon.DTO.UserPokemonDTO;
import FortuneMonBackEnd.fortuneMon.config.security.SecurityUtil;
import FortuneMonBackEnd.fortuneMon.service.UserMonsterBallService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/balls")
@RequiredArgsConstructor
public class UserMonsterBallController {
    private final UserMonsterBallService userMonsterBallService;

    @Operation(summary = "유저가 가진 몬스터볼 개수 조회", description =
            "# 유저 몬스터볼 조회 API입니다. 로그인 후 진행하세요.")
    @GetMapping("")
    public List<UserMonsterBallResponseDTO> getUserMonsterBall(){
        Long userId = SecurityUtil.getCurrentUserId();

        return userMonsterBallService.getUserMonsterBall(userId);
    }

    @Operation(summary = "몬스터볼 오픈", description =
            "# 몬스터볼을 오픈하는 API입니다. 로그인 후 진행하세요.")
    @PostMapping("/{id}/open")
    public UserPokemonDTO openMonsterBall(@PathVariable("id") Long ballId){
        return userMonsterBallService.openMonsterBall(ballId);
    }
}
