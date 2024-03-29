package cz.kotox.common.task.poc.data.impl.di


import android.content.Context
import cz.kotox.common.network.di.CommonRetrofit
import cz.kotox.common.task.poc.data.impl.local.room.DomainTaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainTaskModule {


    val provider = DomainTaskModuleProvider()

    @Provides
    @Singleton
    fun provideDomainTaskDatabase(
        @ApplicationContext context: Context,
    ): DomainTaskDatabase = provider.provideDomainTaskDatabase(context)

    @Singleton
    @Provides
    fun provideTaskDao(db: DomainTaskDatabase) = provider.provideTaskDao(db)

    @Provides
    fun provideTaskApi(@CommonRetrofit retrofit: Retrofit) = provider.provideTaskApi(retrofit)
}
