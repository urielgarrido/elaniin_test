package com.example.elaniin_test.data.model

import com.example.elaniin_test.teams.my_teams.model.Pokemon
import com.google.gson.annotations.SerializedName

data class PokemonsResponse(
    @SerializedName("pokemon_entries") val pokemons: List<PokemonSpecie>
) {
    fun toPokemonModelList() = pokemons.map {
        Pokemon(
            name = it.pokemonSpecieName.name
        )
    }
}

data class PokemonSpecie(
    @SerializedName("pokemon_species") val pokemonSpecieName: PokemonSpecieName
)

data class PokemonSpecieName(
    @SerializedName("name") val name: String
)
