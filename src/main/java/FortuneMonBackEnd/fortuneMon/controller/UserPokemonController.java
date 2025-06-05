package FortuneMonBackEnd.fortuneMon.controller;

import FortuneMonBackEnd.fortuneMon.DTO.UserPokemonDTO;
import FortuneMonBackEnd.fortuneMon.apiPayload.ApiResponse;
import FortuneMonBackEnd.fortuneMon.config.security.SecurityUtil;
import FortuneMonBackEnd.fortuneMon.service.UserPokemonServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserPokemonController {
    private final UserPokemonServiceImpl userPokemonService;

    // 요청 받으면 Service 호출해서 유저가 보유한 포켓몬을 표시하여 반환함
    // 유저의 id를 받아서 id를 기준으로 유저의 포켓몬 정보를 가져온다
    @Operation(summary = "유저 포켓몬 조회", description =
            "# 유저 포켓몬 조회 API입니다. 로그인 후 진행하세요.")
    @GetMapping("/pokemons")
    public List<UserPokemonDTO> getPokemonsWithOwnership(){
        Long userId = SecurityUtil.getCurrentUserId();
        return userPokemonService.getUserPokemonsWithOwnership(userId);
    }

    @Operation(summary = "파트너 포켓몬 설정", description =
            "# 파트너 포켓몬 설정 API입니다. 로그인 후 진행하세요.")
    @PatchMapping("/partners/{id}")
    public ApiResponse<?> setPartnerPokemon(@PathVariable("id") Long pokemonId){
        UserPokemonDTO response = userPokemonService.setPartnerPokemon(pokemonId);
        return ApiResponse.onSuccess(response);
    }

}