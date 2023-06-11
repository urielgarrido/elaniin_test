package com.example.elaniin_test.di

import android.content.Context
import com.example.elaniin_test.sign_in.GoogleAuthUIClient
import com.google.android.gms.auth.api.identity.Identity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GoogleAuthModule {

    @Provides
    fun provideGoogleAuthUIClient(@ApplicationContext applicationContext: Context): GoogleAuthUIClient {
        return GoogleAuthUIClient(
            context = applicationContext,
            signInClient = Identity.getSignInClient(applicationContext)
        )
    }
}