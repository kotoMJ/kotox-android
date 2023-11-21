package cz.kotox.common.task.poc.domain.usecase

import cz.kotox.common.core.android.di.IoDispatcher
import cz.kotox.common.task.poc.data.api.respository.TaskRepository
import cz.kotox.common.task.poc.domain.model.Task
import cz.kotox.common.task.poc.domain.mapper.toTask
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllTasksUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val taskRepository: TaskRepository,
) {
    suspend fun execute(): Flow<List<Task>> =
        taskRepository
            .getAll()
            .map { entities -> entities.map { entity -> entity.toTask() } }
            .flowOn(ioDispatcher)
}

