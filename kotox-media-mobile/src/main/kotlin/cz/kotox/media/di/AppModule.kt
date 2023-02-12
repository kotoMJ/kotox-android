package cz.kotox.media.di

import cz.kotox.android.core.config.AppProperties
import cz.kotox.media.config.KotoxMediaAppProperties
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAppProperties(): AppProperties = KotoxMediaAppProperties()

}