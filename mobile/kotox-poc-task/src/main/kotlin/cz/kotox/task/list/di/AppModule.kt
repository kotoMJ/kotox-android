package cz.kotox.task.list.di

import cz.kotox.android.core.config.AppProperties
import cz.kotox.core.network.config.AppNetworkingProperties
import cz.kotox.task.list.config.KotoxTaskAppNetworkingProperties
import cz.kotox.task.list.config.KotoxTaskAppProperties
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAppProperties(): AppProperties = KotoxTaskAppProperties()

    @Provides
    fun provideAppNetworkingProperties(): AppNetworkingProperties =
        KotoxTaskAppNetworkingProperties()

}
