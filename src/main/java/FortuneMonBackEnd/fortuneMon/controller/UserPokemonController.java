package FortuneMonBackEnd.fortuneMon.controller;

import FortuneMonBackEnd.fortuneMon.DTO.UserPokemonDto;
import FortuneMonBackEnd.fortuneMon.service.UserPokemonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserPokemonController {
    private final UserPokemonService userPokemonService;

    public UserPokemonController(UserPokemonService userPokemonService){
        this.userPokemonService=userPokemonService;
    }

    // 요청 받으면 Service 호출해서 유저가 보유한 포켓몬을 표시하여 반환함
    // 유저의 id를 받아서 id를 기준으로 유저의 포켓몬 정보를 가져온다
    @GetMapping("/users/{id}/pokemons")
    public List<UserPokemonDto> getPokemonsWithOwnership(@PathVariable Long id){
        List<UserPokemonDto> result = userPokemonService.getUserPokemonsWithOwnership(id);
        return result;
    }

}