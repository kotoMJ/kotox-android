package cz.kotox.task.domain.api.usecase

import cz.kotox.task.domain.api.Task
import cz.kotox.task.domain.api.factory.toTask
import cz.kotox.task.domain.api.repository.TaskRepository
import javax.inject.Inject

class GetOneTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend fun execute(taskId: String): Task =
        taskRepository
            .getOne(taskId)
            .toTask()

}

