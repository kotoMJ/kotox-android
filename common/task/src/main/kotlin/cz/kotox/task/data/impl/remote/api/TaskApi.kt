package cz.kotox.task.data.impl.remote.api

import cz.kotox.task.data.impl.remote.dto.TaskDTO
import retrofit2.http.GET

private const val ALL_TASKS_DOCUMENT = "86329e68087a3d2bca54"
interface TaskApi {

    @GET(ALL_TASKS_DOCUMENT)
    suspend fun getAllTasks(): List<TaskDTO>


}