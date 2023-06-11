package com.example.elaniin_test.sign_in

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.elaniin_test.R
import com.example.elaniin_test.sign_in.model.SignInResult
import com.example.elaniin_test.sign_in.model.UserData
import com.facebook.AccessToken
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class AuthUIClient(
    private val context: Context,
    private val signInClient: SignInClient
) {

    private val auth = Firebase.auth

    suspend fun signInGoogle(): IntentSender? {
        val result = try {
            signInClient.beginSignIn(buildBeginSignInRequest()).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun signInFacebook(token: AccessToken): SignInResult {
        val facebookCredential = FacebookAuthProvider.getCredential(token.token)
        return try {
            val user = auth.signInWithCredential(facebookCredential).await().user
            SignInResult(
                data = user?.let {
                    UserData(
                        userId = it.uid,
                        username = it.displayName,
                        profilePictureUrl = it.photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(data = null, errorMessage = e.message)
        }
    }

    suspend fun signInWithIntentGoogle(intent: Intent): SignInResult {
        val credential = signInClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredential).await().user
            SignInResult(
                data = user?.let {
                    UserData(
                        userId = it.uid,
                        username = it.displayName,
                        profilePictureUrl = it.photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(data = null, errorMessage = e.message)
        }
    }

    suspend fun signOut() {
        try {
            signInClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    fun getSignedInUser() = auth.currentUser?.let {
        UserData(
            userId = it.uid,
            username = it.displayName,
            profilePictureUrl = it.photoUrl?.toString()
        )
    }

    private fun buildBeginSignInRequest() = BeginSignInRequest.Builder()
        .setGoogleIdTokenRequestOptions(
            GoogleIdTokenRequestOptions.Builder()
                .setSupported(true)
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.google_web_client_id))
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()
}