package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.UserMonsterBallResponseDTO;
import FortuneMonBackEnd.fortuneMon.DTO.UserPokemonDTO;
import FortuneMonBackEnd.fortuneMon.config.security.SecurityUtil;
import FortuneMonBackEnd.fortuneMon.domain.*;
import FortuneMonBackEnd.fortuneMon.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMonsterBallServiceImpl implements UserMonsterBallService{
    private final UserMonsterBallRepository userMonsterBallRepository;
    private final PokemonBallRateRepository pokemonBallRateRepository;
    private final PokemonRepository pokemonRepository;
    private final UserPokemonRepository userPokemonRepository;
    private final UserRepository userRepository;

    //유저의 몬스터볼 조회
    @Override
    public List<UserMonsterBallResponseDTO> getUserMonsterBall(Long userId){
        List<UserBall> userBalls = userMonsterBallRepository.findByUserId(userId);

        // 이미 연 몬스터볼은 고려하지 않음
        //userBalls.removeIf(UserBall::isOpen);
        List<UserMonsterBallResponseDTO> ballsDTO = userBalls.stream()
                .map(ball -> new UserMonsterBallResponseDTO(
                        ball.getId(),
                        ball.getMonsterBall().getId(),
                        ball.getMonsterBall().getUrl(),
                        ball.getCreatedAt(),
                        ball.isOpen()
                ))
                .collect(Collectors.toList());
        return ballsDTO;
        //return ballCount(userBalls);
    }

    //몬스터볼 오픈 및 포켓몬 도감에 저장
    @Override
    public UserPokemonDTO openMonsterBall(Long ballId){
        double rand = Math.random();
        double cumulative = 0.0;
        PokemonBallRate pokemonRate = null;
        UserBall openBall = userMonsterBallRepository.findById(ballId).orElse(null);
        List<PokemonBallRate> rates = pokemonBallRateRepository.findAllByBallId(openBall.getMonsterBall().getId());

        for(PokemonBallRate rate : rates){
            cumulative += rate.getProbability();
            if(rand < cumulative){
                pokemonRate = rate;
                break;
            }
        }
        if(pokemonRate == null){
            return null;
        }
        Pokemon pokemon = pokemonRepository.findById(pokemonRate.getPokemonId()).orElse(null);
        if(pokemon!=null) {
            openBall.setOpen(true); // 볼 사용 표시
            userMonsterBallRepository.save(openBall);
        }
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userRepository.findById(userId).orElse(null);

        // 이미 보유하고 있는 포켓몬인 경우 db에 추가하지 않고 바로 정보 리턴
        List<UserPokemon> userPokemons = userPokemonRepository.findByUserId(userId);
        for(UserPokemon existPokemon : userPokemons){
            if(existPokemon.getPokemon().getId().equals(pokemon.getId())){
                return new UserPokemonDTO(pokemon.getId(), pokemon.getName(), pokemon.getUrl(), pokemon.getType(),
                        pokemon.getGroupName(), true);
            }
        }

        UserPokemon userPokemon = new UserPokemon();
        userPokemon.setUser(user);
        userPokemon.setPokemon(pokemon);
        userPokemon.setIsPartner(false);
        userPokemonRepository.save(userPokemon); // 도감(유저가 가진 포켓몬) 저장

        return new UserPokemonDTO(pokemon.getId(), pokemon.getName(), pokemon.getUrl(), pokemon.getType(),
                pokemon.getGroupName(), true);
    }

    // 각 몬스터볼의 개수를 세는 함수
    /*private List<UserMonsterBallResponseDTO> ballCount(List<UserBall> userBalls){
        Map<MonsterBall, Long> balls = userBalls.stream()
                .collect(Collectors.groupingBy(UserBall::getMonsterBall, Collectors.counting()));

        return balls.entrySet().stream()
                .map(ball -> new UserMonsterBallResponseDTO(
                        ball.getKey().getBallName(),
                        ball.getKey().getUrl(),
                        ball.getValue()))
                .collect(Collectors.toList());
    }*/
}
