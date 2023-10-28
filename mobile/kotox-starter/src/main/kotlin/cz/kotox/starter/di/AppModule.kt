package cz.kotox.starter.di

import cz.kotox.common.core.config.AppProperties
import cz.kotox.starter.config.KotoxStarterAppProperties
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAppProperties(): AppProperties = KotoxStarterAppProperties()

}
