package cz.kotox.common.camera.custom.capture

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

const val EMPTY_IMAGE_FILE_PATH_NAME: String = "/dev/null"
suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { future ->
        future.addListener(
            {
                continuation.resume(future.get())
            },
            executor
        )
    }
}

val Context.executor: Executor
    get() = ContextCompat.getMainExecutor(this)

suspend fun ImageCapture.takePicture(executor: Executor): File? {

    try {
        val photoFile = withContext(Dispatchers.IO) {
            kotlin.runCatching {
                //Creates file in cache in path data/data/com.aisense.otter.../cache/image12345.jpg
                File.createTempFile("image", "jpg")
            }.getOrElse { ex ->
                Timber.e("TakePicture", "Failed to create temporary file", ex)
                File(EMPTY_IMAGE_FILE_PATH_NAME)
            }
        }

        return suspendCoroutine { continuation ->
            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
            takePicture(
                outputOptions, executor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                        continuation.resume(photoFile)
                    }

                    override fun onError(ex: ImageCaptureException) {
                        Timber.e("TakePicture", "Image capture failed", ex)
                        continuation.resumeWithException(ex)
                    }
                }
            )
        }
    } catch (ice: ImageCaptureException) {
        //https://github.com/kotoMJ/kotox-android/issues/3
        Timber.e(
            ice,
            "Camera issue when taking picture. " +
                    "Maybe the app was sent to background after triggering a picture but before picture was saved."
        )
        return null
    }
}
