package cz.kotox.common.task.poc.data.api.respository

import cz.kotox.common.task.poc.data.impl.local.room.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getAll(): Flow<List<TaskEntity>>

    suspend fun getOne(taskId: String): TaskEntity

    suspend fun insertAll(taskEntities: List<TaskEntity>)

    suspend fun getLocalImagePath(taskId: String): String?

    suspend fun insertLocalImagePath(taskId: String, localPath: String)
}