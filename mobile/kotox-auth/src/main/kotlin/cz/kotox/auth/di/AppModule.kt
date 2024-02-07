package cz.kotox.auth.di

import android.content.Context
import cz.kotox.auth.config.KotoxAuthAppProperties
import cz.kotox.common.core.config.AppProperties
import cz.kotox.common.designsystem.extension.isDarkMode
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppProperties(@ApplicationContext appContext: Context): AppProperties = KotoxAuthAppProperties(
        isDarkMode = appContext.isDarkMode()
    )

}
