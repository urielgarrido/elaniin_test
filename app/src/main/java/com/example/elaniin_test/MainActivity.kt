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
import com.example.elaniin_test.regions.RegionsViewModel
import com.example.elaniin_test.sign_in.AuthUIClient
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

                            LaunchedEffect(Unit) {
                                viewModel.getRegions()
                            }

                            RegionsScreen(state, state.regions)
                        }
                        composable("teams") {
                            TeamsScreen(navController = navController)
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
}

