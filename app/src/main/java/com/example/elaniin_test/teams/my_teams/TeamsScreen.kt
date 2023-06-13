package com.example.elaniin_test.teams.my_teams

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elaniin_test.R
import com.example.elaniin_test.teams.TeamsState
import com.example.elaniin_test.teams.my_teams.model.Team
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamsScreen(
    state: TeamsState,
    onClickCreateTeam: () -> Unit,
    onClickTeam: (Team) -> Unit,
    onClickCopyTeam: (String) -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = state.errorId) {
        state.errorId?.let { errorId ->
            Toast.makeText(context, context.getString(errorId), Toast.LENGTH_SHORT).show()
        }
    }

    var token: String by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TeamsTopBar(context, state.regionName) },
        floatingActionButton = { CreateTeamFAB(onClickCreateTeam) }
    ) { paddingValues ->

        if (state.teams.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = context.getString(R.string.create_your_first_team),
                    fontSize = 24.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                CopyTeamFromTokenTextField(token) { token = it }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { onClickCopyTeam(token) }) {
                    Text(text = "Copiar equipo")
                }
            }
        } else {
            LazyColumn(
                contentPadding = paddingValues,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    CopyTeamFromTokenTextField(token) { token = it }
                }
                item {
                    Button(onClick = { onClickCopyTeam(token) }) {
                        Text(text = "Copiar equipo")
                    }
                }
                items(state.teams) {
                    TeamItem(it) { team ->
                        onClickTeam(team)
                    }
                }
            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CopyTeamFromTokenTextField(token: String, onChangeText: (String) -> Unit) {
    OutlinedTextField(
        value = token, onValueChange = onChangeText, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        singleLine = true,
        label = {
            Text(text = "Ingresa el token que te compartieron")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun CreateTeamFAB(onClickCreateTeam: () -> Unit) {
    FloatingActionButton(onClick = onClickCreateTeam) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "")
    }
}

@Composable
fun TeamItem(team: Team, onClickTeam: (Team) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(1.dp, Color.Black)
            .clickable {
                onClickTeam(team)
            }, verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        Text(
            text = team.name.replaceFirstChar { it.uppercaseChar() },
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)
        )
        Text(
            text = "Pokemons: ${team.pokemons.map { it.name }}",
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Text(
            text = "Token: ${team.shareToken}",
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamsTopBar(context: Context, regionName: String) {
    TopAppBar(title = {
        Text(text = "Region: ${regionName.replaceFirstChar { it.uppercaseChar() }}")
    }, modifier = Modifier.fillMaxWidth(), actions = {
        IconButton(onClick = {
            Firebase.auth.signOut()
            (context as ComponentActivity).finishAffinity()
        }) {
            Icon(painter = painterResource(id = R.drawable.ic_logout), contentDescription = "")
        }
    })
}
