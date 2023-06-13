package com.example.elaniin_test.teams.create_team

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elaniin_test.R
import com.example.elaniin_test.teams.TeamsState
import com.example.elaniin_test.teams.my_teams.TeamsTopBar
import com.example.elaniin_test.teams.my_teams.model.Pokemon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTeamScreen(state: TeamsState, onContinueButton: (List<Pokemon>) -> Unit) {

    val context = LocalContext.current

    LaunchedEffect(key1 = state.errorId) {
        state.errorId?.let { errorId ->
            Toast.makeText(context, context.getString(errorId), Toast.LENGTH_SHORT).show()
        }
    }

    var pokemonsSelected by remember { mutableStateOf<List<PokemonUIState>>(emptyList()) }

    val pokemonsUiState: List<PokemonUIState> = state.pokemons.map { PokemonUIState(it.name) }

    Scaffold(
        topBar = { TeamsTopBar(context, state.regionName) },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            Button(onClick = {
                if (pokemonsSelected.size < 3) {
                    Toast.makeText(context, context.getString(R.string.min_three_pokemons), Toast.LENGTH_SHORT).show()
                } else
                    onContinueButton(pokemonsSelected.map { Pokemon(it.name) })
            }) {
                Text(text = "Continuar", fontSize = 16.sp)
            }
        }
    ) { paddingValues ->

        if (pokemonsUiState.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                contentPadding = paddingValues,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    Text(
                        text = context.getString(R.string.select_pokemons_for_your_team),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                item {
                    Text(
                        text = "Pokemons seleccionados: ${pokemonsSelected.map { it.name }}",
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                items(pokemonsUiState) {
                    PokemonItem(it) { pokemonSelected ->
                        if (pokemonsSelected.indexOf(pokemonSelected) == -1) {
                            if (pokemonsSelected.size < 6) {
                                pokemonsUiState.find { it == pokemonSelected }!!.isSelected = !pokemonSelected.isSelected
                                pokemonsSelected = pokemonsSelected + pokemonSelected
                            } else {
                                Toast.makeText(context, context.getString(R.string.max_pokemons_selected), Toast.LENGTH_SHORT)
                                    .show()
                            }
                        } else {
                            pokemonsUiState.find { it == pokemonSelected }!!.isSelected = !pokemonSelected.isSelected
                            pokemonsSelected = pokemonsSelected - pokemonSelected
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun PokemonItem(pokemon: PokemonUIState, onPokemonClick: (PokemonUIState) -> Unit) {
    OutlinedButton(
        onClick = {
            onPokemonClick(pokemon)
        },
        shape = RectangleShape,
        border = if (pokemon.isSelected) {
            BorderStroke(2.dp, Color.Green)
        } else {
            ButtonDefaults.outlinedButtonBorder
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = pokemon.name.replaceFirstChar { it.uppercaseChar() }, fontSize = 16.sp)
        Spacer(modifier = Modifier.weight(1f))
    }
}
