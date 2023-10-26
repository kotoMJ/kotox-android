package cz.kotox.task.domain.api.usecase

import cz.kotox.common.task.data.api.respository.TaskRepository
import cz.kotox.task.domain.api.model.Task
import cz.kotox.task.domain.api.factory.toTask
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

