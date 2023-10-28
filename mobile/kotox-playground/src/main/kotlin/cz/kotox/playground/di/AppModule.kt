package cz.kotox.playground.di

import cz.kotox.common.core.config.AppProperties
import cz.kotox.playground.config.KotoxPlaygroundAppProperties
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAppProperties(): AppProperties = KotoxPlaygroundAppProperties()

}
