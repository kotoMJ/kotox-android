package cz.kotox.feature.firebase.auth.username.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import cz.kotox.auth.domain.service.AccountService
import cz.kotox.auth.domain.service.AccountServiceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides fun auth(): FirebaseAuth = Firebase.auth
}

@Module
@InstallIn(SingletonComponent::class)
abstract class FirebaseAuthModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
}
