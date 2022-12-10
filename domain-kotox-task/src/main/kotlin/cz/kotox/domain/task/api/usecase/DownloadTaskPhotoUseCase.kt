package cz.kotox.domain.task.api.usecase

import android.content.Context
import cz.kotox.domain.task.api.factory.toTask
import cz.kotox.domain.task.api.repository.TaskRepository
import cz.kotox.domain.task.impl.remote.image.ImageDownloader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class DownloadTaskPhotoUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend fun execute(context: Context, taskId: String): Boolean {

        return withContext(Dispatchers.IO) {
            val imageUrl = taskRepository
                .getOne(taskId)
                .toTask().image

            val localImageUrl = ImageDownloader().download(
                context = context,
                imageUrl = imageUrl,
                taskId = taskId
            )

            Timber.d(">>>_ localImageUrl: $localImageUrl")

            if (localImageUrl != null) {
                taskRepository.insertLocalImagePath(taskId, localImageUrl)
                true
            } else {
                false
            }
        }

    }
}

