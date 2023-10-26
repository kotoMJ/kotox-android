package cz.kotox.task.domain.api.usecase

import cz.kotox.common.task.data.api.respository.TaskRepository
import cz.kotox.task.domain.api.model.Task
import cz.kotox.task.domain.api.factory.toTask
import javax.inject.Inject

class GetOneTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend fun execute(taskId: String): Task =
        taskRepository
            .getOne(taskId)
            .toTask()

}

