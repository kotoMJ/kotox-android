package cz.kotox.domain.task.api.usecase

import cz.kotox.domain.task.api.repository.TaskRepository
import javax.inject.Inject

class GetOneTaskImageUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend fun execute(taskId: String): String? =
        taskRepository
            .getLocalImagePath(taskId)

}

