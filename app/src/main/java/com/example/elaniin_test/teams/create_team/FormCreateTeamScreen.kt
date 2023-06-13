package com.example.elaniin_test.teams.create_team

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elaniin_test.teams.TeamsState
import com.example.elaniin_test.teams.my_teams.TeamsTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormCreateTeamScreen(state: TeamsState, onCreateTeam: (teamName: String, shareToken: String) -> Unit) {

    val context = LocalContext.current

    LaunchedEffect(key1 = state.errorId) {
        state.errorId?.let { errorId ->
            Toast.makeText(context, context.getString(errorId), Toast.LENGTH_SHORT).show()
        }
    }

    var teamName: String by remember { mutableStateOf("") }
    var shareToken: String by remember { mutableStateOf("") }

    Scaffold(topBar = { TeamsTopBar(context, state.regionName) }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = teamName,
                onValueChange = { teamName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = {
                    Text(text = "Nombre del equipo")
                },
                singleLine = true
            )

            OutlinedTextField(
                value = shareToken,
                onValueChange = { if (it.length <= 6) shareToken = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = {
                    Text(text = "Token para compartir con otro usuario")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            Button(onClick = {
                if (teamName.isNotBlank() && shareToken.isNotBlank())
                    onCreateTeam(teamName, shareToken)
                else Toast.makeText(context, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            }, modifier = Modifier.align(CenterHorizontally)) {
                Text(text = "Crear equipo", fontSize = 16.sp)
            }
        }

    }
}