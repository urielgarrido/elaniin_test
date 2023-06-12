package com.example.elaniin_test.teams

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.elaniin_test.R
import com.example.elaniin_test.teams.model.Team
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamsScreen(regionName: String, teams: List<Team>) {

    val context = LocalContext.current

    Scaffold(
        topBar = { TeamsTopBar(context, regionName) },
        floatingActionButton = { CreateTeamFAB() }
    ) { paddingValues ->

        if (teams.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = context.getString(R.string.create_your_first_team), fontSize = 24.sp)
            }
        } else {
            LazyColumn(contentPadding = paddingValues) {
                items(teams) {
                    TeamItem(it) {

                    }
                }
            }
        }

    }

}

@Composable
fun CreateTeamFAB() {
    FloatingActionButton(onClick = {  }) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "")
    }
}

@Composable
fun TeamItem(team: Team, onClickTeam: (Team) -> Unit) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamsTopBar(context: Context, regionName: String) {
    TopAppBar(title = {
        Text(text = "Region: $regionName")
    }, modifier = Modifier.fillMaxWidth(), actions = {
        IconButton(onClick = {
            Firebase.auth.signOut()
            (context as ComponentActivity).finishAffinity()
        }) {
            Icon(painter = painterResource(id = R.drawable.ic_logout), contentDescription = "")
        }
    })
}
