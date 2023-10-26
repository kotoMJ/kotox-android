package cz.kotox.common.task.data.impl.repository

import cz.kotox.common.task.data.api.respository.TaskRepository
import cz.kotox.common.task.data.impl.local.room.TaskDao
import cz.kotox.common.task.data.impl.local.room.TaskEntity
import cz.kotox.common.task.data.impl.local.room.TaskLocalImageEntity
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


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

