package cz.kotox.task.domain.api.usecase

import cz.kotox.android.core.error.BasicError
import cz.kotox.core.network.apicall.apiCall
import cz.kotox.core.network.apicall.mapErrorBasic
import cz.kotox.android.core.result.fold
import cz.kotox.common.task.data.api.respository.TaskRepository
import cz.kotox.task.domain.api.factory.toTaskEntity
import cz.kotox.common.task.data.impl.remote.api.TaskApi
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class RefreshTasksUseCase @Inject constructor(
    private val taskApi: TaskApi,
    private val taskRepository: TaskRepository
) {

    suspend fun execute(onError: (error: BasicError) -> Unit, onSuccess: () -> Unit) {
        withContext(kotlinx.coroutines.Dispatchers.IO) {
            try {
                apiCall(taskApi::getAllTasks)
                    .mapErrorBasic().fold({ dtos ->
                        taskRepository.insertAll(dtos.map { dto -> dto.toTaskEntity() })
                        onSuccess.invoke()
                    }, {
                        onError.invoke(it)
                    })
            } catch (e: Exception) {
                //Handling exception here might solve e.g. http 503 (Service Unavailable)
                Timber.e(e, "Unexpected issue when loading data from API")
                onError.invoke(cz.kotox.android.core.error.UnknownError)
            }
        }
    }

}