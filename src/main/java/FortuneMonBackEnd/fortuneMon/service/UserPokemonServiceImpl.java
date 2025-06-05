package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.UserPokemonDTO;
import FortuneMonBackEnd.fortuneMon.config.security.SecurityUtil;
import FortuneMonBackEnd.fortuneMon.domain.Pokemon;
import FortuneMonBackEnd.fortuneMon.domain.UserPokemon;
import FortuneMonBackEnd.fortuneMon.repository.PokemonRepository;
import FortuneMonBackEnd.fortuneMon.repository.UserPokemonRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPokemonServiceImpl implements UserPokemonService {

    private final PokemonRepository pokemonRepository;
    private final UserPokemonRepository userPokemonRepository;


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
                Arrays.stream(pokemon.getType().split(","))
                                .map(String::trim)
                                .collect(Collectors.toList()),
                pokemon.getGroupName(),
                userOwnedPokemon.contains(pokemon.getId()),
                isPartner(pokemon.getId())
        )).collect(Collectors.toList());
    }

    //파트너 설정
    public UserPokemonDTO setPartnerPokemon(Long pokemonId){
        Long userId = SecurityUtil.getCurrentUserId();
        List<UserPokemon> userPokemons = userPokemonRepository.findByUserId(userId);
        UserPokemonDTO userPokemonDTO = null;
        for(UserPokemon userPokemon : userPokemons){
            if(userPokemon.getPokemon().getId().equals(pokemonId)){
                userPokemon.setIsPartner(true);
                userPokemonDTO = new UserPokemonDTO(userPokemon.getPokemon().getId(), userPokemon.getPokemon().getName(),
                        userPokemon.getPokemon().getUrl(), Arrays.stream(userPokemon.getPokemon().getType().split(","))
                        .map(String::trim)
                        .collect(Collectors.toList()),
                        userPokemon.getPokemon().getGroupName(), true, userPokemon.getIsPartner());
                userPokemonRepository.save(userPokemon);
            }
            else if(userPokemon.getIsPartner()){
                userPokemon.setIsPartner(false);
                userPokemonRepository.save(userPokemon);
            }
        }
        if(userPokemonDTO==null){
            throw new IllegalArgumentException("보유하고 있지 않은 포켓몬입니다");
        }
        return userPokemonDTO;
    }

    private boolean isPartner(Long pokemonId){
        Long userId = SecurityUtil.getCurrentUserId();
        List<UserPokemon> userPokemons = userPokemonRepository.findByUserId(userId);
        for(UserPokemon userPokemon : userPokemons){
            if(userPokemon.getPokemon().getId().equals(pokemonId)){
                if(userPokemon.getIsPartner()){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        return false;
    }
}