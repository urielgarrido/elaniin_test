package com.example.elaniin_test.teams.model

data class Team(
    val name: String,
    val pokemons: List<Pokemon>,
    val shareToken: String
)

data class Pokemon(
    val name: String,
    val image: String?
)
