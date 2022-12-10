package cz.kotox.domain.task.impl.di


import android.content.Context
import androidx.room.Room
import cz.kotox.core.network.di.CommonRetrofit
import cz.kotox.domain.task.impl.local.database.DomainTaskDatabase
import cz.kotox.domain.task.impl.remote.api.TaskApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainTaskModule {

    @Provides
    @Singleton
    fun provideDomainTaskDatabase(
        @ApplicationContext context: Context,
    ): DomainTaskDatabase = Room.databaseBuilder(
        context,
        DomainTaskDatabase::class.java,
        "android-domain-task-database"
    ).build()

    @Singleton
    @Provides
    fun provideWalletDao(db: DomainTaskDatabase) = db.taskDao()

    @Provides
    fun provideTaskApi(@CommonRetrofit retrofit: Retrofit) = retrofit.create<TaskApi>()
}
