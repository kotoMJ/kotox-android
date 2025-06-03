package cz.kotox.starter.di

import android.content.Context
import cz.kotox.common.core.config.AppProperties
import cz.kotox.common.designsystem.extension.isDarkMode
import cz.kotox.starter.config.KotoxStarterAppProperties
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAppProperties(
        @ApplicationContext appContext: Context
    ): AppProperties = KotoxStarterAppProperties(
        isDarkMode = appContext.isDarkMode()
    )

}
