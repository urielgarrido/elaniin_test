package com.example.elaniin_test.teams.create_team.data

import com.example.elaniin_test.teams.my_teams.model.Pokemon
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val pokemonDataSource: PokemonDataSource) {

    suspend fun getPokemons(pokedexId: Int): List<Pokemon> {
        return pokemonDataSource.getPokemons(pokedexId)?.toPokemonModelList() ?: emptyList()
    }
}