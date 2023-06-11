package com.example.elaniin_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.elaniin_test.login.LoginScreen
import com.example.elaniin_test.regions.RegionsScreen
import com.example.elaniin_test.teams.TeamsScreen
import com.example.elaniin_test.ui.theme.Elaniin_testTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Elaniin_testTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(navController = navController)
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

