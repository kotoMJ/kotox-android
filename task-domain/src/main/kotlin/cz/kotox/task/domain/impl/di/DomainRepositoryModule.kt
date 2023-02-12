package cz.kotox.task.domain.impl.di

import cz.kotox.task.domain.api.repository.TaskRepository
import cz.kotox.task.domain.api.repository.TaskRepositoryImpl
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