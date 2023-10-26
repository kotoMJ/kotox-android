package cz.kotox.task.domain.api.usecase

import android.content.Context
import cz.kotox.common.task.data.api.respository.TaskRepository
import cz.kotox.common.task.data.impl.remote.image.ImageDownloader
import cz.kotox.task.domain.api.factory.toTask
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

            //TODO MJ - think about imageDownloader being testable and being in the data.
            // Is it worth to change it somehow?
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

