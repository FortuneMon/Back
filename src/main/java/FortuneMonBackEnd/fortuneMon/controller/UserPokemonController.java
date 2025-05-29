package FortuneMonBackEnd.fortuneMon.controller;

import FortuneMonBackEnd.fortuneMon.DTO.UserPokemonDTO;
import FortuneMonBackEnd.fortuneMon.config.security.SecurityUtil;
import FortuneMonBackEnd.fortuneMon.service.UserPokemonServiceImpl;
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
    @GetMapping("/pokemons")
    public List<UserPokemonDTO> getPokemonsWithOwnership(){
        Long userId = SecurityUtil.getCurrentUserId();
        return userPokemonService.getUserPokemonsWithOwnership(userId);
    }

    @PatchMapping("/partners/{id}")
    public UserPokemonDTO setPartnerPokemon(@PathVariable("id") Long pokemonId){
        return userPokemonService.setPartnerPokemon(pokemonId);
    }

}