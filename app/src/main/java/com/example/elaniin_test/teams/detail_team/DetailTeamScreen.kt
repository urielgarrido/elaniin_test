package com.example.elaniin_test.teams.detail_team

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elaniin_test.R
import com.example.elaniin_test.teams.TeamsState
import com.example.elaniin_test.teams.create_team.PokemonItem
import com.example.elaniin_test.teams.my_teams.model.Pokemon
import com.example.elaniin_test.teams.my_teams.model.Team

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTeamScreen(state: TeamsState, onEditTeam: (String, List<Pokemon>) -> Unit, onDeleteTeam: () -> Unit) {

    val context = LocalContext.current

    LaunchedEffect(key1 = state.errorId) {
        state.errorId?.let { errorId ->
            Toast.makeText(context, context.getString(errorId), Toast.LENGTH_SHORT).show()
        }
    }

    var teamName by remember { mutableStateOf(state.teamSelected!!.name) }
    var pokemonsSelected by remember { mutableStateOf(state.teamSelected!!.pokemons) }

    Scaffold(
        topBar = { TeamSelectedTopBar(state.teamSelected, onDeleteTeam) },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            Button(onClick = {
                if (pokemonsSelected.size < 3) {
                    Toast.makeText(context, context.getString(R.string.min_three_pokemons), Toast.LENGTH_SHORT).show()
                } else
                    onEditTeam(teamName, pokemonsSelected.map { Pokemon(it.name) })
            }) {
                Text(text = "Editar equipo", fontSize = 16.sp)
            }
        }
    )
    { paddingValues ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Edita o elimina este equipo",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = teamName,
                onValueChange = { teamName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = {
                    Text(text = "Nuevo nombre del equipo")
                },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Pokemons: ${pokemonsSelected.map { it.name }}", modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(state.pokemons) {
                    PokemonItem(it) { pokemonSelected ->
                        if (pokemonsSelected.indexOf(pokemonSelected) == -1) {
                            if (pokemonsSelected.size < 6) {
                                pokemonsSelected = pokemonsSelected + pokemonSelected
                            } else {
                                Toast.makeText(context, context.getString(R.string.max_pokemons_selected), Toast.LENGTH_SHORT)
                                    .show()
                            }
                        } else {
                            pokemonsSelected = pokemonsSelected - pokemonSelected
                        }
                    }
                }
            }
        }

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamSelectedTopBar(teamSelected: Team?, onDeleteTeam: () -> Unit) {
    TopAppBar(title = {
        Text(text = "Equipo: ${teamSelected!!.name}")
    }, actions = {
        IconButton(onClick = onDeleteTeam) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "")
        }
    })
}