package cz.kotox.common.camera.custom.capture

import android.Manifest
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.view.ScaleGestureDetector
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.UseCase
import androidx.camera.core.ZoomState
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import cz.kotox.common.core.android.permission.Permission
import cz.kotox.common.camera.custom.R
import cz.kotox.common.camera.custom.LensFacing
import cz.kotox.common.camera.custom.utils.AppSettingsIntentUtils.getAppSettingsIntent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import timber.log.Timber
import kotlin.time.Duration.Companion.seconds

private const val SCALE_GESTURE_COUNTDOWN_IN_SECONDS = 3

data class CameraCaptureInput(
    val currentSelector: LensFacing? = null,
    val currentZoomValues: ZoomValues?,
    val zoomStateObserver: Observer<ZoomState>
)

@OptIn(ExperimentalPermissionsApi::class)
@ExperimentalCoroutinesApi
@androidx.compose.ui.tooling.preview.Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun CameraCapture(
    @PreviewParameter(CameraCapturePreviewProvider::class) input: CameraCaptureInput,
    modifier: Modifier = Modifier,
    onEventHandler: (CameraScreenEvent) -> Unit = {}
) {
    val context = LocalContext.current

    val backgroundColor = Color.Black

    var camera: Camera? by remember { mutableStateOf<Camera?>(null) }

    var gestureDetected by remember { mutableStateOf<Boolean>(false) }
    var gestureDetectedCountDownSeconds by remember { mutableStateOf(0) }

    var scaleGestureDetector: ScaleGestureDetector? by remember {
        mutableStateOf(null)
    }

    var previewView: PreviewView? by remember { mutableStateOf<PreviewView?>(null) }

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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = backgroundColor)
            ) {

                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = stringResource(id = R.string.camera_capture_permission_not_available),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        OutlinedButton(
                            modifier = Modifier.padding(top = 16.dp),
                            border = BorderStroke(2.dp, Color.White),
                            contentPadding = PaddingValues(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.White,
                                backgroundColor = Color.Black
                            ),
                            onClick = {
                                context.startActivity(getAppSettingsIntent(context))
                            }) {
                            Text(text = "Permission settings", color = Color.White)
                        }
                    }
                }

            }

        }
    ) {
        if (input.currentSelector == null || input.currentSelector == LensFacing.NOT_DETECTED) {
            if (input.currentSelector == LensFacing.NOT_DETECTED) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = backgroundColor)
                ) {

                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = stringResource(id = R.string.camera_capture_camera_not_detected),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            if (isLandscape()) {
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

            if (!isLandscape()) {
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

@Composable
internal fun launchCameraUseCase(
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
                lifecycleOwner, cameraSelector, previewUseCase, imageCaptureUseCase
            )
            onCameraBind.invoke(camera)
        } catch (ex: Exception) {
            Log.e("CameraCapture", "Failed to bind camera use cases", ex)
        }
    }
}

class CameraCapturePreviewProvider : PreviewParameterProvider<CameraCaptureInput> {
    override val values: Sequence<CameraCaptureInput> = sequenceOf(
        CameraCaptureInput(LensFacing.BACK, currentZoomValues = null, Observer { }),
        CameraCaptureInput(LensFacing.FRONT, currentZoomValues = null, Observer { })
    )
}
