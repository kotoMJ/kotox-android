package cz.kotox.task.list.di

import cz.kotox.android.core.config.AppProperties
import cz.kotox.task.list.config.AndroidTemplateAppProperties
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAppProperties(): AppProperties = AndroidTemplateAppProperties()

}
