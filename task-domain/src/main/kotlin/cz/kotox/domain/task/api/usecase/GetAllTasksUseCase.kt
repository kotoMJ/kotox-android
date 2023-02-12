package cz.kotox.domain.task.api.usecase

import cz.kotox.domain.task.api.Task
import cz.kotox.domain.task.api.factory.toTask
import cz.kotox.domain.task.api.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend fun execute(): Flow<List<Task>> =
        taskRepository
            .getAll()
            .map { entities -> entities.map { entity -> entity.toTask() } }
            .flowOn(kotlinx.coroutines.Dispatchers.IO)
}

