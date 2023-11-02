package cz.kotox.common.core.android.screencapture

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Looper
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import cz.kotox.common.core.android.permission.getReadMediaImagesPermission
import java.lang.ref.WeakReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import timber.log.Timber

class LegacyScreenCaptureDetectionDelegate(
    private val activityReference: WeakReference<Activity>,
    private val listener: ScreenshotDetectionListener
) {

    private var job: Job? = null

    constructor(
        activity: Activity,
        listener: ScreenshotDetectionListener
    ) : this(WeakReference(activity), listener)

    @Suppress("unused")
    constructor(
        activity: Activity,
        onScreenCaptured: (path: String) -> Unit,
        onPotentialScreenCaptured: () -> Unit
    ) : this(
        WeakReference(activity),
        object : ScreenshotDetectionListener {
            override fun onScreenCaptured(path: String) {
                onScreenCaptured(path)
            }

            override fun onPotentialScreenCaptured() {
                onPotentialScreenCaptured.invoke()
            }
        }
    )

    @FlowPreview
    @ExperimentalCoroutinesApi
    fun startScreenshotDetection() {
        Timber.d(">>>_ startScreenshotDetection")
        job = CoroutineScope(Dispatchers.Main).launch {
            createContentObserverFlow()
                .debounce(500)
                .collect { uri ->
                    Timber.d(">>>_ startScreenshotDetection.uri: $uri")
                    activityReference.get()?.let { activity ->
                        onContentChanged(activity, uri)
                    }
                }
        }
    }

    fun stopScreenshotDetection() {
        Timber.d(">>>_ stopScreenshotDetection")
        job?.cancel()
    }

    @ExperimentalCoroutinesApi
    fun createContentObserverFlow() = channelFlow {
        val contentObserver = object : ContentObserver(android.os.Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean, uri: Uri?) {
                uri?.let { _ ->
                    trySend(uri)
                }
            }
        }
        activityReference.get()
            ?.contentResolver
            ?.registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                true,
                contentObserver
            )
        awaitClose {
            activityReference.get()
                ?.contentResolver
                ?.unregisterContentObserver(contentObserver)
        }
    }

    private fun onContentChanged(context: Context, uri: Uri) {
        if (isReadExternalStoragePermissionGranted()) {
            val path = getFilePathFromContentResolver(context, uri)
            Timber.d(">>>_ startScreenshotDetection.path: $path")
            path?.let { p ->
                if (isScreenshotPath(p)) {
                    listener.onScreenCaptured(p)
                }
            }
        } else {
            listener.onPotentialScreenCaptured()
        }
    }

    private fun isScreenshotPath(path: String?): Boolean {
        val lowercasePath = path?.lowercase()
        val screenshotDirectory = getPublicScreenshotDirectoryName()?.lowercase()
        return (
            screenshotDirectory != null &&
                lowercasePath?.contains(screenshotDirectory) == true
            ) ||
            lowercasePath?.contains("screenshot") == true
    }

    private fun getPublicScreenshotDirectoryName() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_SCREENSHOTS).name
    } else {
        null
    }

    @Suppress("TooGenericExceptionCaught")
    private fun getFilePathFromContentResolver(context: Context, uri: Uri): String? {
        try {
            context.contentResolver.query(
                uri,
                arrayOf(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATA
                ),
                null,
                null,
                null
            )?.let { cursor ->
                cursor.moveToFirst()
                val path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
                cursor.close()
                return path
            }
        } catch (e: Exception) {
            Timber.e(e, e.message ?: "")
        }
        return null
    }

    private fun isReadExternalStoragePermissionGranted(): Boolean {
        return activityReference.get()?.let { activity ->
            ContextCompat.checkSelfPermission(
                activity,
                getReadMediaImagesPermission()
            ) == PackageManager.PERMISSION_GRANTED
        } ?: run {
            false
        }
    }

    interface ScreenshotDetectionListener {
        fun onScreenCaptured(path: String)
        fun onPotentialScreenCaptured()
    }
}
