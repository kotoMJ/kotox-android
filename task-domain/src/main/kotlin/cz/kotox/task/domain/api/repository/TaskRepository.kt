package cz.kotox.task.domain.api.repository

import cz.kotox.task.domain.impl.local.database.TaskDao
import cz.kotox.task.domain.impl.local.database.TaskEntity
import cz.kotox.task.domain.impl.local.database.TaskLocalImageEntity
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

interface TaskRepository {

    fun getAll(): Flow<List<TaskEntity>>

    suspend fun getOne(taskId: String): TaskEntity

    suspend fun insertAll(taskEntities: List<TaskEntity>)

    suspend fun getLocalImagePath(taskId: String): String?

    suspend fun insertLocalImagePath(taskId: String, localPath: String)
}


@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {
    override fun getAll(): Flow<List<TaskEntity>> = taskDao.getTasks()

    override suspend fun getOne(taskId: String): TaskEntity = taskDao.getTask(taskId)

    override suspend fun insertAll(taskEntities: List<TaskEntity>) {
        Timber.d(">>>_ inserting list of size: ${taskEntities.size}")
        taskDao.insertAll(taskEntities)
    }

    override suspend fun getLocalImagePath(taskId: String): String? {
        return try {
            taskDao.getTaskLocalImage(taskId)?.localImageUrl
        } catch (e: Exception) {
            Timber.e(e, "Unable to obtain local image for taskId: ${taskId}")
            null
        }
    }

    override suspend fun insertLocalImagePath(taskId: String, localPath: String) {
        taskDao.insertTaskLocalImage(TaskLocalImageEntity(taskId, localPath))
    }
}

