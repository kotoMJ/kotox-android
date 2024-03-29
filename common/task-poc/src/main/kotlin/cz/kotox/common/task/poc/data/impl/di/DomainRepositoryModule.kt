package cz.kotox.common.task.poc.data.impl.di

import cz.kotox.common.task.poc.data.api.respository.TaskRepository
import cz.kotox.common.task.poc.data.impl.repository.TaskRepositoryImpl
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