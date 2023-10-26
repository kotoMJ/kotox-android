package cz.kotox.common.task.data.impl.di

import android.content.Context
import androidx.room.Room
import cz.kotox.core.network.di.CommonRetrofit
import cz.kotox.common.task.data.impl.local.room.DomainTaskDatabase
import cz.kotox.common.task.data.impl.remote.api.TaskApi
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.create

class DomainTaskModuleProvider {

    fun provideDomainTaskDatabase(
        @ApplicationContext context: Context,
    ): DomainTaskDatabase = Room.databaseBuilder(
        context,
        DomainTaskDatabase::class.java,
        "android-domain-task-database"
    ).build()

    fun provideTaskDao(db: DomainTaskDatabase) = db.taskDao()

    fun provideTaskApi(@CommonRetrofit retrofit: Retrofit) = retrofit.create<TaskApi>()
}