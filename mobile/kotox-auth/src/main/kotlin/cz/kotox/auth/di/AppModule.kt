package cz.kotox.auth.di

import android.content.Context
import cz.kotox.auth.data.config.KotoxAuthAppProperties
import cz.kotox.auth.domain.service.AccountService
import cz.kotox.auth.domain.service.AccountServiceImpl
import cz.kotox.common.core.config.AppProperties
import cz.kotox.common.designsystem.extension.isDarkMode
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModuleProvider {

    @Provides
    @Singleton
    fun provideAppProperties(@ApplicationContext appContext: Context): AppProperties = KotoxAuthAppProperties(
        isDarkMode = appContext.isDarkMode()
    )
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
}
