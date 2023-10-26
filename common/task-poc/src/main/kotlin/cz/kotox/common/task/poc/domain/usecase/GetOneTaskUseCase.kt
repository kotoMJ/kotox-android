package cz.kotox.common.task.poc.domain.usecase

import cz.kotox.common.task.poc.data.api.respository.TaskRepository
import cz.kotox.common.task.poc.domain.model.Task
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

