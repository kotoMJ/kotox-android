package cz.kotox.camera.custom.capture

import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import cz.kotox.camera.custom.LensFacing
import kotlinx.coroutines.launch
import timber.log.Timber

private const val SHOW_SLIDER_COUNTDOWN_IN_SECONDS = 3

@Composable
fun CameraCaptureLandscape(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Black,
    currentSelector: LensFacing? = null,
    currentZoomValues: ZoomValues? = null,
    gestureDetected: Boolean = false,
    onEventHandler: (CameraScreenEvent) -> Unit = {},
    onCameraBind: (camera: Camera) -> Unit,
    onCameraUnbind: () -> Unit,
    onUpdateZoomRatio: (zoomRatio: Float) -> Unit,
    onPreviewViewCreated: (PreviewView) -> Unit = {}
) {

    val context = LocalContext.current

    Box(modifier = modifier.background(color = backgroundColor)) {
        val lifecycleOwner = LocalLifecycleOwner.current
        val coroutineScope = rememberCoroutineScope()
        var previewUseCase by remember { mutableStateOf<UseCase>(Preview.Builder().build()) }
        val imageCaptureUseCase by remember {
            mutableStateOf(
                ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                    .build()
            )
        }


        CameraPreview(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.CenterStart)
                .padding(end = 148.dp),
            onUseCase = {
                previewUseCase = it
            },
            onPreviewViewCreated = onPreviewViewCreated
        )

        Row(
            modifier = Modifier.align(Alignment.BottomEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CaptureFlipCameraButton(
                modifier = Modifier
                    .size(100.dp)
                    .padding(24.dp)
            ) {
                onEventHandler.invoke(CameraScreenEvent.SwitchCameraSelector)
            }
        }

        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (currentZoomValues != null) {
                CaptureZoomToggle(
                    onLongPress = {
                        Timber.d(">>>_ SLIDER CaptureZoomToggle longpress")
                    },
                    onChange = { zoomRatio ->
                        onUpdateZoomRatio.invoke(zoomRatio)
                    },
                    input = CaptureZoomToggleInput(
                        modifier = Modifier
                            .padding(end = 16.dp),
                        zoomValues = currentZoomValues,
                        lensFacing = currentSelector,
                        showVertical = true
                    )
                )
            }

            CapturePictureButton(
                modifier = Modifier
                    .size(100.dp)
                    .padding(16.dp),
                onClick = {
                    coroutineScope.launch {
                        onEventHandler.invoke(
                            CameraScreenEvent.CaptureImageFile(
                                imageCaptureUseCase.takePicture(context.executor)
                            )
                        )
                    }
                }
            )
        }

        when (currentSelector) {
            LensFacing.BACK -> {
                launchCameraUseCase(
                    previewUseCase,
                    context,
                    lifecycleOwner,
                    imageCaptureUseCase,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    { onCameraBind(it) },
                    { onCameraUnbind() })
            }

            LensFacing.FRONT -> {
                launchCameraUseCase(
                    previewUseCase,
                    context,
                    lifecycleOwner,
                    imageCaptureUseCase,
                    CameraSelector.DEFAULT_FRONT_CAMERA,
                    { onCameraBind(it) },
                    { onCameraUnbind() })
            }

            else -> {
                Timber.w("No camera lens facing detected on the device!")
            }
        }
    }
}

