package cz.kotox.common.camera.custom.capture.layout.multi

import android.Manifest
import android.content.Context
import android.content.res.Configuration
import android.view.ScaleGestureDetector
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.UseCase
import androidx.camera.view.PreviewView
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import cz.kotox.common.camera.custom.LensFacing
import cz.kotox.common.camera.custom.capture.CameraCaptureInput
import cz.kotox.common.camera.custom.capture.CameraNotDetectedScreenContent
import cz.kotox.common.camera.custom.capture.CameraScreenEvent
import cz.kotox.common.camera.custom.capture.getCameraProvider
import cz.kotox.common.camera.custom.capture.permission.PermissionNotAvailableScreenContent
import cz.kotox.common.camera.custom.capture.zoom.createScaleGestureDetector
import cz.kotox.common.core.android.permission.Permission
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import timber.log.Timber
import kotlin.time.Duration.Companion.seconds

private const val SCALE_GESTURE_COUNTDOWN_IN_SECONDS = 3


@OptIn(ExperimentalPermissionsApi::class)
@ExperimentalCoroutinesApi
@Composable
fun CameraCaptureMultiLayout(
    input: CameraCaptureInput,
    modifier: Modifier = Modifier,
    onEventHandler: (CameraScreenEvent) -> Unit = {}
) {
    val context = LocalContext.current

    val backgroundColor = Color.Black

    var camera: Camera? by remember { mutableStateOf(null) }

    var gestureDetected by remember { mutableStateOf(false) }
    var gestureDetectedCountDownSeconds by remember { mutableIntStateOf(0) }

    var scaleGestureDetector: ScaleGestureDetector? by remember {
        mutableStateOf(null)
    }

    var previewView: PreviewView? by remember { mutableStateOf(null) }

    val lifecycleOwner = LocalLifecycleOwner.current

    fun PreviewView.setListener(scaleGestureDetector: ScaleGestureDetector?) {
        setOnTouchListener { _, event ->
            scaleGestureDetector?.onTouchEvent(event)
            return@setOnTouchListener true
        }
    }

    @Composable
    fun isLandscape() =
        LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    fun onCameraBind(it: Camera) {
        Timber.d(">>>_ Register camera...")
        scaleGestureDetector = createScaleGestureDetector(context, it) {
            gestureDetectedCountDownSeconds = SCALE_GESTURE_COUNTDOWN_IN_SECONDS
            gestureDetected = true
        }
        camera = it

        camera?.cameraInfo?.zoomState?.observe(lifecycleOwner, input.zoomStateObserver)
        previewView?.setListener(scaleGestureDetector)
    }

    fun onCameraUnbind() {
        Timber.d(">>>_ Unregister camera...")
        camera?.cameraInfo?.zoomState?.removeObserver(input.zoomStateObserver)

        scaleGestureDetector = null
        camera = null
    }

    fun setLinearZoom(value: Float) {
        camera?.cameraControl?.setLinearZoom(value)
    }

    fun setZoomRatio(value: Float) {
        camera?.cameraControl?.setZoomRatio(value)
    }

    fun onPreviewViewCreated(it: PreviewView) {
        Timber.d(">>>_ PreviewViewCreated...")
        previewView = it
    }

    Permission(
        permission = Manifest.permission.CAMERA,
        permissionNotAvailableContent = {
            PermissionNotAvailableScreenContent(backgroundColor, context)
        }
    ) {
        if (input.currentSelector == null || input.currentSelector == LensFacing.NOT_DETECTED) {
            if (input.currentSelector == LensFacing.NOT_DETECTED) {
                CameraNotDetectedScreenContent(backgroundColor)
            }
        } else {
            Crossfade(targetState = isLandscape()) {
                when (it) {
                    true -> {
                        CameraCaptureLandscape(
                            modifier = modifier,
                            backgroundColor = backgroundColor,
                            currentSelector = input.currentSelector,
                            onEventHandler = onEventHandler,
                            onCameraBind = { camera ->
                                onCameraBind(camera)
                            },
                            onCameraUnbind = { onCameraUnbind() },
                            onUpdateZoomRatio = { zoomRatio ->
                                setZoomRatio(zoomRatio)
                            },
                            currentZoomValues = input.currentZoomValues,
                            onPreviewViewCreated = { onPreviewViewCreated(it) },
                            gestureDetected = gestureDetected
                        )
                    }

                    false -> {
                        CameraCapturePortrait(
                            modifier = modifier,
                            backgroundColor = backgroundColor,

                            currentSelector = input.currentSelector,
                            onEventHandler = onEventHandler,
                            onCameraBind = { camera ->
                                onCameraBind(camera)
                            },
                            onCameraUnbind = { onCameraUnbind() },
                            onUpdateZoomRatio = { zoomRatio ->
                                setZoomRatio(zoomRatio)
                            },
                            onUpdateLinearZoomValue = { linearZoomValue ->
                                setLinearZoom(linearZoomValue)
                            },
                            currentZoomValues = input.currentZoomValues,
                            onPreviewViewCreated = { onPreviewViewCreated(it) },
                            gestureDetected = gestureDetected
                        )
                    }
                }
            }
        }
    }
    LaunchedEffect(gestureDetected) {
        Timber.d(">>>_ GESTURE detected $gestureDetected")
        if (gestureDetected) {
            while (gestureDetectedCountDownSeconds > 0) {
                delay(1.seconds)
                gestureDetectedCountDownSeconds -= 1
                Timber.d(">>>_ GESTURE countdown decreased $gestureDetected")
            }
            gestureDetected = false
        }
    }
}

@SuppressWarnings("TooGenericExceptionCaught")
@Composable
internal fun LaunchCameraUseCase(
    previewUseCase: UseCase,
    context: Context,
    lifecycleOwner: LifecycleOwner,
    imageCaptureUseCase: ImageCapture,
    cameraSelector: CameraSelector,
    onCameraBind: (camera: Camera) -> Unit,
    onCameraUnbind: () -> Unit
) {
    LaunchedEffect(previewUseCase) {
        val cameraProvider = context.getCameraProvider()
        try {
            // Must unbind the use-cases before rebinding them.
            onCameraUnbind.invoke()
            cameraProvider.unbindAll()
            val camera = cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                previewUseCase,
                imageCaptureUseCase
            )
            onCameraBind.invoke(camera)
        } catch (ex: Exception) {
            Timber.e(ex, "Failed to bind camera use cases")
        }
    }
}
