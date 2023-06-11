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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.elaniin_test.regions.RegionsScreen
import com.example.elaniin_test.sign_in.GoogleAuthUIClient
import com.example.elaniin_test.sign_in.SignInScreen
import com.example.elaniin_test.sign_in.SignInViewModel
import com.example.elaniin_test.teams.TeamsScreen
import com.example.elaniin_test.ui.theme.Elaniin_testTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var googleAuthUIClient: GoogleAuthUIClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Elaniin_testTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "sign_in") {
                        composable("sign_in") {
                            val viewModel: SignInViewModel = hiltViewModel()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUIClient.signInWithIntent(result.data ?: return@launch)
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isSignInSuccess) {
                                if (state.isSignInSuccess) {
                                    navController.navigate("regions")
                                }
                            }

                            SignInScreen(
                                state = state,
                                onSignInGoogle = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUIClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(signInIntentSender ?: return@launch).build()
                                        )
                                    }
                                },
                                onSignInFacebook = {}
                            )
                        }
                        composable("regions") {
                            RegionsScreen(navController = navController)
                        }
                        composable("teams") {
                            TeamsScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

