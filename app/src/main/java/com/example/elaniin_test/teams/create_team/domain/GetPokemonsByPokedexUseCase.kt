package com.example.elaniin_test.teams.create_team.domain

import com.example.elaniin_test.teams.create_team.data.PokemonRepository
import com.example.elaniin_test.teams.my_teams.model.Pokemon
import javax.inject.Inject

class GetPokemonsByPokedexUseCase @Inject constructor(private val pokemonRepository: PokemonRepository) {

    suspend operator fun invoke(pokedexId: Int): List<Pokemon> {
        return pokemonRepository.getPokemons(pokedexId)
    }
}