package cz.kotox.task.domain.impl.di


import android.content.Context
import androidx.room.Room
import cz.kotox.core.network.di.CommonRetrofit
import cz.kotox.task.domain.impl.local.database.DomainTaskDatabase
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
