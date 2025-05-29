package FortuneMonBackEnd.fortuneMon.service;

import FortuneMonBackEnd.fortuneMon.DTO.UserPokemonDTO;

import java.util.List;

public interface UserPokemonService {
    List<UserPokemonDTO> getUserPokemonsWithOwnership(Long userId);

    UserPokemonDTO setPartnerPokemon(Long pokemonId);
}
