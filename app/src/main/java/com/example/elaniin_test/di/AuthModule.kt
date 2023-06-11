package com.example.elaniin_test.di

import android.content.Context
import com.example.elaniin_test.sign_in.AuthUIClient
import com.google.android.gms.auth.api.identity.Identity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    fun provideAuthUIClient(@ApplicationContext applicationContext: Context): AuthUIClient {
        return AuthUIClient(
            context = applicationContext,
            signInClient = Identity.getSignInClient(applicationContext)
        )
    }
}