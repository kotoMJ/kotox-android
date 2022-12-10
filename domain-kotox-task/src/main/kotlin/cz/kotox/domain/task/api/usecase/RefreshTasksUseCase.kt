package cz.kotox.domain.task.api.usecase

import cz.kotox.android.core.error.BasicError
import cz.kotox.core.network.apicall.apiCall
import cz.kotox.core.network.apicall.mapErrorBasic
import cz.kotox.android.core.result.fold
import cz.kotox.domain.task.api.factory.toTaskEntity
import cz.kotox.domain.task.api.repository.TaskRepository
import cz.kotox.domain.task.impl.remote.api.TaskApi
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RefreshTasksUseCase @Inject constructor(
    private val taskApi: TaskApi,
    private val taskRepository: TaskRepository
) {

    suspend fun execute(onError: (error: BasicError) -> Unit, onSuccess: () -> Unit) {
        withContext(kotlinx.coroutines.Dispatchers.IO) {
            apiCall(taskApi::getAllTasks)
                .mapErrorBasic().fold({ dtos ->
                    taskRepository.insertAll(dtos.map { dto -> dto.toTaskEntity() })
                    onSuccess.invoke()
                }, {
                    onError.invoke(it)
                })
        }
    }

}