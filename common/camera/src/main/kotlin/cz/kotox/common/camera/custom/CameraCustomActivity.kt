package cz.kotox.common.camera.custom

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.OrientationEventListener
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.camera.core.CameraInfoUnavailableException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.common.util.concurrent.ListenableFuture
import cz.kotox.common.camera.custom.capture.CameraScreenAdaptiveLayout
import cz.kotox.common.camera.custom.capture.CameraScreenEvent
import cz.kotox.common.camera.custom.capture.CameraScreenMultiLayout
import cz.kotox.common.camera.custom.capture.CameraScreenViewState
import cz.kotox.common.camera.custom.capture.EMPTY_IMAGE_FILE_PATH_NAME
import cz.kotox.common.core.android.extension.lockDeviceRotationPortrait
import cz.kotox.common.core.android.extension.serializable
import cz.kotox.common.core.extension.exhaustive
import cz.kotox.common.designsystem.theme.KotoxBasicTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

val EMPTY_IMAGE_URI: Uri = Uri.parse("file:/$EMPTY_IMAGE_FILE_PATH_NAME")
const val ARG_CAMERA_LAYOUT = "ARG_CAMERA_LAYOUT"

const val UNKNOWN = -1
const val PORTRAIT = 0
const val PORTRAIT_REV = 2
const val LANDSCAPE = 1
const val LANDSCAPE_REV = 3

enum class CameraCustomLayout {
    Adaptive,
    Multi
}

@OptIn(
    ExperimentalCoroutinesApi::class
)
@AndroidEntryPoint
class CameraCustomActivity : ComponentActivity() {

    private val viewModel: CameraActivityViewModel by viewModels()

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    private lateinit var orientationListener: OrientationEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cameraLayout: CameraCustomLayout = intent.extras?.serializable<CameraCustomLayout>(
            ARG_CAMERA_LAYOUT
        ) ?: CameraCustomLayout.Adaptive

        cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            try {
                viewModel.setAvailableCameraSelectors(cameraProvider)
            } catch (e: CameraInfoUnavailableException) {
                Timber.e(e, "Unable to set available camera selectors")
            }
        }, ContextCompat.getMainExecutor(this))
        cameraProviderFuture.get()

        @Suppress("MagicNumber")
        orientationListener = object : OrientationEventListener(this) {
            override fun onOrientationChanged(orientation: Int) {
                when (orientation) {
                    in 0..359 -> {
                        viewModel.setCurrentCameraRotation(orientation)
                    }

                    -1 -> {
                        // Unable to get orientation from the sensor - e.g. phone is lying on the desk
                    }

                    else -> {
                        Timber.e("Illegal degree for rotation $orientation !")
                    }
                }
            }
        }

        lockDeviceRotationPortrait(cameraLayout == CameraCustomLayout.Adaptive)

        setContent {
            val orientationViewState: State<OrientationViewState> = viewModel.orientationViewState.collectAsStateWithLifecycle()
            KotoxBasicTheme {
                when (cameraLayout) {
                    CameraCustomLayout.Adaptive -> {
                        Timber.d(
                            ">>>_ orientation: ${orientationViewState.value.cameraOrientation}"
                        )
                        CameraScreenAdaptiveLayout(
                            input = CameraScreenViewState(
                                currentCameraSelector = viewModel.currentCameraSelectorPresenter.value,
                                currentZoomValues = viewModel.currentZoomValuesPresenter.value,
                                zoomStateObserver = viewModel.zoomStateObserver
                            ),
                            orientation = orientationViewState,
                            modifier = Modifier.fillMaxSize()
                        ) { event ->
                            when (event) {
                                is CameraScreenEvent.SwitchCameraSelector -> {
                                    viewModel.setNextCameraSelector()
                                }

                                is CameraScreenEvent.ExitCamera -> {
                                    exitActivity(event.uri)
                                }

                                is CameraScreenEvent.CaptureImageFile -> {
                                    // Event is processed on the CameraCapture level only.
                                }
                            }.exhaustive
                        }
                    }

                    CameraCustomLayout.Multi -> {
                        CameraScreenMultiLayout(
                            input = CameraScreenViewState(
                                currentCameraSelector = viewModel.currentCameraSelectorPresenter.value,
                                currentZoomValues = viewModel.currentZoomValuesPresenter.value,
                                zoomStateObserver = viewModel.zoomStateObserver
                            ),
                            modifier = Modifier.fillMaxSize()
                        ) { event ->
                            when (event) {
                                is CameraScreenEvent.SwitchCameraSelector -> {
                                    viewModel.setNextCameraSelector()
                                }

                                is CameraScreenEvent.ExitCamera -> {
                                    exitActivity(event.uri)
                                }

                                is CameraScreenEvent.CaptureImageFile -> {
                                    // Event is processed on the CameraCapture level only.
                                }
                            }.exhaustive
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        orientationListener.enable()
    }

    override fun onPause() {
        orientationListener.disable()
        super.onPause()
    }

    private fun exitActivity(uri: Uri) {
        setResult(
            if (uri == EMPTY_IMAGE_URI) RESULT_CANCELED else RESULT_OK,
            Intent().apply {
                if (uri != EMPTY_IMAGE_URI) {
                    Timber.d(">>>_ Notify updates on model.")
                    putExtra(ARG_OUT_PHOTO_URI, uri)
                } else {
                    Timber.d(">>>_ No update on model.")
                }
            }
        )
        finish()
    }
}
