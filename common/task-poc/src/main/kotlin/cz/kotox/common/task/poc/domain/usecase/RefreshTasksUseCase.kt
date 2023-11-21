package cz.kotox.common.task.poc.domain.usecase

import cz.kotox.common.core.android.di.IoDispatcher
import cz.kotox.common.core.error.BasicError
import cz.kotox.common.core.error.UnknownError
import cz.kotox.common.network.apicall.apiCall
import cz.kotox.common.network.apicall.mapErrorBasic
import cz.kotox.common.core.result.fold
import cz.kotox.common.task.poc.data.api.respository.TaskRepository
import cz.kotox.common.task.poc.domain.mapper.toTaskEntity
import cz.kotox.common.task.poc.data.impl.remote.api.TaskApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class RefreshTasksUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val taskApi: TaskApi,
    private val taskRepository: TaskRepository
) {

    suspend fun execute(onError: (error: BasicError) -> Unit, onSuccess: () -> Unit) {
        withContext(ioDispatcher) {
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
                onError.invoke(UnknownError)
            }
        }
    }

}