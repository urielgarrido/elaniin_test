package com.example.elaniin_test.teams

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elaniin_test.R
import com.example.elaniin_test.regions.domain.GetRegionByIdUseCase
import com.example.elaniin_test.teams.create_team.domain.GetPokemonsByPokedexUseCase
import com.example.elaniin_test.teams.my_teams.domain.CreateTeamUseCase
import com.example.elaniin_test.teams.my_teams.domain.GetAllTeamsByRegionUseCase
import com.example.elaniin_test.teams.my_teams.model.Pokemon
import com.example.elaniin_test.teams.my_teams.model.Team
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAllTeamsByRegionUseCase: GetAllTeamsByRegionUseCase,
    private val getRegionByIdUseCase: GetRegionByIdUseCase,
    private val getPokemonsByPokedexUseCase: GetPokemonsByPokedexUseCase,
    private val createTeamUseCase: CreateTeamUseCase
) : ViewModel() {

    private val regionName: String = checkNotNull(savedStateHandle["regionName"])
    private val regionId: Int = checkNotNull(savedStateHandle["regionId"])

    private val _state = MutableStateFlow(TeamsState(regionName, regionId))
    val state = _state.asStateFlow()

    fun getPokemonsByPokedex() {
        viewModelScope.launch {
            val resultRegion = getRegionByIdUseCase(regionId)
            if (resultRegion != null) {
                val resultPokemons = getPokemonsByPokedexUseCase(resultRegion.first().getId())
                _state.update {
                    it.copy(pokemons = resultPokemons)
                }
            } else {
                _state.update {
                    it.copy(errorId = R.string.get_pokedexes_error)
                }
            }
        }
    }

    fun getAllTeamsByRegion(valueEventListener: ValueEventListener) {
        viewModelScope.launch {
            getAllTeamsByRegionUseCase(valueEventListener, regionName)
        }
    }

    fun createTeam(teamName: String, shareToken: String) {
        val newTeam = Team(teamName, state.value.pokemonsSelected, shareToken, regionName)
        viewModelScope.launch {
            val result = createTeamUseCase(newTeam)
            if (result) {
                _state.update {
                    it.copy(teams = it.teams + newTeam)
                }
            }
        }
    }

    fun editTeam() {

    }

    fun deleteTeam() {

    }

    fun setSelectedPokemons(pokemons: List<Pokemon>) {
        _state.update {
            it.copy(
                pokemonsSelected = pokemons
            )
        }
    }

    fun setMyTeams(teams: List<Team>) {
        _state.update {
            it.copy(teams = teams)
        }
    }

}