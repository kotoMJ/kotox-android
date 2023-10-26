package cz.kotox.task.data.impl.di

import cz.kotox.task.data.api.respository.TaskRepository
import cz.kotox.task.data.impl.repository.TaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainRepositoryModule {
    @Binds
    abstract fun bindTaskRepository(repository: TaskRepositoryImpl): TaskRepository
}