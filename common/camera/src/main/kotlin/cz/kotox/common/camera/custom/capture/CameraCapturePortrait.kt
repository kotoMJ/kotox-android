package cz.kotox.common.camera.custom.capture

import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import cz.kotox.common.camera.custom.LensFacing
import cz.kotox.common.camera.custom.capture.button.CaptureFlipCameraButton
import cz.kotox.common.camera.custom.capture.button.CapturePictureButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.time.Duration.Companion.seconds

private const val SHOW_SLIDER_COUNTDOWN_IN_SECONDS = 3

@Composable
fun CameraCapturePortrait(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Black,
    currentSelector: LensFacing? = null,
    currentZoomValues: ZoomValues? = null,
    gestureDetected: Boolean = false,
    onEventHandler: (CameraScreenEvent) -> Unit = {},
    onCameraBind: (camera: Camera) -> Unit,
    onCameraUnbind: () -> Unit,
    onUpdateLinearZoomValue: (linearZoomValue: Float) -> Unit,
    onUpdateZoomRatio: (zoomRatio: Float) -> Unit,
    onPreviewViewCreated: (PreviewView) -> Unit = {}
) {

    val context = LocalContext.current

    var showSlider by remember { mutableStateOf<Boolean>(false) }
    var showSliderCountDownSeconds by remember { mutableStateOf(0) }

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
                .align(Alignment.TopCenter)
                .padding(bottom = 148.dp),
            onUseCase = {
                previewUseCase = it
            },
            onPreviewViewCreated = onPreviewViewCreated
        )

        Column(
            modifier = Modifier.align(Alignment.BottomStart),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CaptureFlipCameraButton(
                modifier = Modifier
                    .size(100.dp)
                    .padding(24.dp)
            ) {
                onEventHandler.invoke(CameraScreenEvent.SwitchCameraSelector)
            }
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (currentZoomValues != null) {
                AnimatedVisibility(visible = !showSlider && !gestureDetected) {
                    Timber.d(">>>_ TOGGLE CaptureZoomToggle")
                    CaptureZoomToggle(
                        onLongPress = {
                            Timber.d(">>>_ TOGGLE CaptureZoomToggle longpress")
                            showSliderCountDownSeconds = SHOW_SLIDER_COUNTDOWN_IN_SECONDS
                            showSlider = true
                        },
                        onChange = { zoomRatio ->
                            onUpdateZoomRatio.invoke(zoomRatio)
                        },
                        input = CaptureZoomToggleInput(
                            modifier = Modifier
                                .padding(bottom = 16.dp),
                            zoomValues = currentZoomValues,
                            showVertical = false,
                            lensFacing = currentSelector
                        )
                    )
                }
                AnimatedVisibility(visible = showSlider || gestureDetected) {
                    Timber.d(">>>_ SLIDER CaptureZoomSlider")
                    CaptureZoomSlider(
                        input = CaptureZoomSliderInput(
                            zoomValues = currentZoomValues,
                            showVertical = false
                        ),
                        onValueChange = { linearZoomValue ->
                            showSliderCountDownSeconds = SHOW_SLIDER_COUNTDOWN_IN_SECONDS
                            showSlider = true
                            onUpdateLinearZoomValue.invoke(linearZoomValue)
                        }
                    )
                }
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
                },
            )
        }


        when (currentSelector) {
            LensFacing.BACK -> {
                LaunchCameraUseCase(
                    previewUseCase,
                    context,
                    lifecycleOwner,
                    imageCaptureUseCase,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    { onCameraBind(it) },
                    { onCameraUnbind() })
            }

            LensFacing.FRONT -> {
                LaunchCameraUseCase(
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

    LaunchedEffect(showSlider) {
        Timber.d(">>>_ SLIDER showSlider $showSlider")
        if (showSlider) {
            while (showSliderCountDownSeconds > 0) {
                delay(1.seconds)
                showSliderCountDownSeconds -= 1
                Timber.d(">>>_ SLIDER showSlider decreased $showSlider")
            }
            showSlider = false
        }
    }
}

