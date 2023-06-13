package com.example.elaniin_test.teams

import com.example.elaniin_test.data.model.Pokedex
import com.example.elaniin_test.teams.my_teams.model.Pokemon
import com.example.elaniin_test.teams.my_teams.model.Team

data class TeamsState(
    val regionName: String,
    val regionId: Int,
    val pokedexes: List<Pokedex> = emptyList(),
    val pokemons: List<Pokemon> = emptyList(),
    val pokemonsSelected: List<Pokemon> = emptyList(),
    val teams: List<Team> = emptyList(),
    val errorId: Int? = null
)
