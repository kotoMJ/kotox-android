package cz.kotox.task.domain.api.usecase

import cz.kotox.common.task.poc.data.api.respository.TaskRepository
import javax.inject.Inject

class GetOneTaskImageUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend fun execute(taskId: String): String? =
        taskRepository
            .getLocalImagePath(taskId)

}

