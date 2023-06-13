package com.example.elaniin_test.teams.my_teams.model

data class Team(
    val name: String,
    val pokemons: List<Pokemon>,
    val shareToken: String,
    val region: String,
) {
    constructor() : this("", emptyList(), "", "")
}

data class Pokemon(
    val name: String
) {
    constructor(): this("")
}
