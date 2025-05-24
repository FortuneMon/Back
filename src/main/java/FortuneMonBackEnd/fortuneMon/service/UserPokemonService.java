package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.UserPokemonDTO;
import FortuneMonBackEnd.fortuneMon.domain.Pokemon;
import FortuneMonBackEnd.fortuneMon.domain.UserPokemon;
import FortuneMonBackEnd.fortuneMon.repository.PokemonRepository;
import FortuneMonBackEnd.fortuneMon.repository.UserPokemonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserPokemonService {

    private final PokemonRepository pokemonRepository;
    private final UserPokemonRepository userPokemonRepository;

    public UserPokemonService(PokemonRepository pokemonRepository, UserPokemonRepository userPokemonRepository){
        this.pokemonRepository=pokemonRepository;
        this.userPokemonRepository=userPokemonRepository;
    }

    public List<UserPokemonDTO> getUserPokemonsWithOwnership(Long userId){
        // DB에 존재하는 모든 포켓몬 가져오기
        List<Pokemon> allPokemons = pokemonRepository.findAll();

        // 유저가 가진 포켓몬 가져오기
        List<UserPokemon> userPokemons = userPokemonRepository.findByUserId(userId);

        // 유저가 가진 포켓몬의 Id(전체 포켓몬이 저장된 테이블에서의 Id) 가져오기
        Set<Long> userOwnedPokemon = userPokemons.stream().map(pokemon -> pokemon.getPokemon().getId())
                .collect(Collectors.toSet());

        // 전체 포켓몬 Id를 참조하면서 유저가 가진 포켓몬 Id Set에 있는지를 확인하고 boolean 타입으로 저장하고 return
        return allPokemons.stream().map(pokemon -> new UserPokemonDTO(
                pokemon.getId(),
                pokemon.getName(),
                pokemon.getUrl(),
                pokemon.getType(),
                pokemon.getGroupName(),
                userOwnedPokemon.contains(pokemon.getId())
        )).collect(Collectors.toList());
    }
}