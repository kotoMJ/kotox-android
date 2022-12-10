package cz.kotox.domain.task.api.usecase

import cz.kotox.domain.task.api.Task
import cz.kotox.domain.task.api.factory.toTask
import cz.kotox.domain.task.api.repository.TaskRepository
import javax.inject.Inject

class GetOneTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend fun execute(taskId: String): Task =
        taskRepository
            .getOne(taskId)
            .toTask()

}

