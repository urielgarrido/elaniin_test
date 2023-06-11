package com.example.elaniin_test.sign_in

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SignInScreen(state: SignInState, onSignInGoogle: () -> Unit, onSignInFacebook: () -> Unit) {

    val context = LocalContext.current

    LaunchedEffect(key1 = state.signInErrorMessage) {
        state.signInErrorMessage?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ButtonSignInWithGoogle(onSignInGoogle)
            ButtonSignInWithFacebook(onSignInFacebook)
        }
    }

}

@Composable
fun ButtonSignInWithGoogle(onSignInGoogle: () -> Unit) {
    Button(onClick = onSignInGoogle, contentPadding = PaddingValues(horizontal = 8.dp)) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "")
        Text(text = "Sign-In Google", fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Composable
fun ButtonSignInWithFacebook(onSignInFacebook: () -> Unit) {
    Button(onClick = onSignInFacebook, contentPadding = PaddingValues(horizontal = 8.dp)) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "")
        Text(text = "Sign-In Facebook", fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}