package cz.kotox.strings.di

import cz.kotox.android.core.config.AppProperties
import cz.kotox.strings.config.KotoxStringsAppProperties
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAppProperties(): AppProperties = KotoxStringsAppProperties()

}
