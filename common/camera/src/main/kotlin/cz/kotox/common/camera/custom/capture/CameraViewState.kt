package cz.kotox.common.camera.custom.capture

import android.net.Uri
import androidx.camera.core.ZoomState
import androidx.lifecycle.Observer
import cz.kotox.common.camera.custom.LensFacing
import java.io.File

data class CameraScreenViewState(
    val currentCameraSelector: LensFacing?,
    val currentZoomValues: ZoomValues?,
    val zoomStateObserver: Observer<ZoomState>
)

sealed class CameraScreenEvent {
    object SwitchCameraSelector : CameraScreenEvent()
    data class ExitCamera(val uri: Uri) : CameraScreenEvent()
    data class CaptureImageFile(val file: File?) : CameraScreenEvent()
}

data class CameraCaptureInput(
    val currentSelector: LensFacing? = null,
    val currentZoomValues: ZoomValues?,
    val zoomStateObserver: Observer<ZoomState>
)