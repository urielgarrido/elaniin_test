package com.example.elaniin_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.elaniin_test.regions.RegionsScreen
import com.example.elaniin_test.regions.RegionsViewModel
import com.example.elaniin_test.sign_in.AuthUIClient
import com.example.elaniin_test.sign_in.SignInScreen
import com.example.elaniin_test.sign_in.SignInViewModel
import com.example.elaniin_test.teams.TeamsViewModel
import com.example.elaniin_test.teams.create_team.CreateTeamScreen
import com.example.elaniin_test.teams.create_team.FormCreateTeamScreen
import com.example.elaniin_test.teams.detail_team.DetailTeamScreen
import com.example.elaniin_test.teams.my_teams.TeamsScreen
import com.example.elaniin_test.teams.my_teams.model.Team
import com.example.elaniin_test.ui.theme.Elaniin_testTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authUIClient: AuthUIClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Elaniin_testTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = getStartDestination()) {
                        composable("sign_in") {
                            val viewModel: SignInViewModel = hiltViewModel()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = authUIClient.signInWithIntentGoogle(result.data ?: return@launch)
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isSignInSuccess) {
                                if (state.isSignInSuccess) {
                                    navController.navigate("regions") {
                                        popUpTo("sign_in") {
                                            inclusive = true
                                        }
                                    }
                                }
                            }

                            SignInScreen(
                                state = state,
                                onSignInGoogle = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = authUIClient.signInGoogle()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(signInIntentSender ?: return@launch).build()
                                        )
                                    }
                                },
                                onSignInFacebook = {
                                    lifecycleScope.launch {
                                        val signInResult = authUIClient.signInFacebook(it.accessToken)
                                        viewModel.onSignInResult(signInResult)
                                    }
                                }
                            )
                        }
                        composable("regions") {
                            val viewModel: RegionsViewModel = hiltViewModel()
                            val state by viewModel.state.collectAsStateWithLifecycle()
                            val userName = authUIClient.getSignedInUser()!!.username!!

                            LaunchedEffect(Unit) {
                                viewModel.getRegions()
                            }

                            RegionsScreen(state, userName, onRegionClick = {
                                navController.navigate("teams/${it.name}/${it.getId()}")
                            })
                        }
                        navigation("teams/{regionName}/{regionId}", "teams_flow") {
                            composable("teams/{regionName}/{regionId}",
                                arguments = listOf(
                                    navArgument("regionName") { type = NavType.StringType },
                                    navArgument("regionId") { type = NavType.IntType }
                                )
                            ) { backStackEntry ->
                                val viewModel: TeamsViewModel = backStackEntry.sharedViewModel(navController)
                                val state by viewModel.state.collectAsStateWithLifecycle()

                                val myTeams = object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        val dataList = mutableListOf<Team>()

                                        for (childSnapshot in snapshot.children) {
                                            val data = childSnapshot.getValue(Team::class.java)
                                            data?.let { dataList.add(it) }
                                        }

                                        viewModel.setMyTeams(dataList.toList())

                                    }

                                    override fun onCancelled(error: DatabaseError) {

                                    }
                                }

                                LaunchedEffect(Unit) {
                                    viewModel.getAllTeamsByRegion(myTeams)
                                }

                                LaunchedEffect(Unit) {
                                    viewModel.getPokemonsByPokedex()
                                }

                                TeamsScreen(
                                    state,
                                    onClickCreateTeam = { navController.navigate("create_team") },
                                    onClickTeam = {
                                        navController.navigate("detail_team")
                                        viewModel.setTeamSelected(it)
                                    }
                                )
                            }
                            composable("create_team") { backStackEntry ->
                                val viewModel: TeamsViewModel = backStackEntry.sharedViewModel(navController)
                                val state by viewModel.state.collectAsStateWithLifecycle()

                                LaunchedEffect(Unit) {
                                    viewModel.getPokemonsByPokedex()
                                }

                                CreateTeamScreen(state) {
                                    navController.navigate("form_create_team")
                                    viewModel.setSelectedPokemons(it)
                                }
                            }
                            composable("form_create_team") { backStackEntry ->
                                val viewModel: TeamsViewModel = backStackEntry.sharedViewModel(navController)
                                val state by viewModel.state.collectAsStateWithLifecycle()

                                FormCreateTeamScreen(state) { teamName, shareToken ->
                                    navController.navigate("teams/${state.regionName}/${state.regionId}") {
                                        popUpTo("teams_flow") {
                                            inclusive = true
                                        }
                                    }
                                    viewModel.createTeam(teamName, shareToken)
                                }
                            }
                            composable("detail_team") { backStackEntry ->
                                val viewModel: TeamsViewModel = backStackEntry.sharedViewModel(navController)
                                val state by viewModel.state.collectAsStateWithLifecycle()

                                DetailTeamScreen(
                                    state,
                                    onEditTeam = { teamName, pokemons ->
                                        navController.navigate("teams/${state.regionName}/${state.regionId}") {
                                            popUpTo("teams_flow") {
                                                inclusive = true
                                            }
                                        }
                                        viewModel.editTeam(teamName, pokemons)
                                    },
                                    onDeleteTeam = {
                                        navController.navigate("teams/${state.regionName}/${state.regionId}") {
                                            popUpTo("teams_flow") {
                                                inclusive = true
                                            }
                                        }
                                        viewModel.deleteTeam()
                                    }
                                )
                            }

                        }

                    }
                }
            }
        }
    }

    private fun getStartDestination(): String {
        val isLogin = authUIClient.getSignedInUser() != null

        return if (isLogin) {
            "regions"
        } else "sign_in"
    }

    @Composable
    inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
        val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
        val parentEntry = remember(this) {
            navController.getBackStackEntry(navGraphRoute)
        }
        return hiltViewModel(parentEntry)
    }
}

