package com.example.elaniin_test.teams.create_team.data

import com.example.elaniin_test.data.PokemonApi
import com.example.elaniin_test.data.model.PokemonsResponse
import javax.inject.Inject

class PokemonDataSource @Inject constructor(private val pokemonApi: PokemonApi) {

    suspend fun getPokemons(pokedexId: Int): PokemonsResponse? {
        return pokemonApi.getPokemons(pokedexId).body()
    }

}