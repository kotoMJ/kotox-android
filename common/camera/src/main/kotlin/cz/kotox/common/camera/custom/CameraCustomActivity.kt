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
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import cz.kotox.common.camera.custom.capture.CameraScreenContent
import cz.kotox.common.camera.custom.capture.CameraScreenEvent
import cz.kotox.common.camera.custom.capture.CameraScreenViewState
import cz.kotox.common.camera.custom.capture.EMPTY_IMAGE_FILE_PATH_NAME
import cz.kotox.common.core.extension.exhaustive
import cz.kotox.common.designsystem.theme.KotoxBasicTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

val EMPTY_IMAGE_URI: Uri = Uri.parse("file:/$EMPTY_IMAGE_FILE_PATH_NAME")
const val UNKNOWN = -1
const val PORTRAIT = 0
const val PORTRAIT_REV = 2
const val LANDSCAPE = 1
const val LANDSCAPE_REV = 3

@OptIn(
    ExperimentalCoroutinesApi::class,
)
@AndroidEntryPoint
class CameraCustomActivity : ComponentActivity() {

    private val viewModel: CameraActivityViewModel by viewModels()

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    private lateinit var orientationListener: OrientationEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                /**
                 * FIXME MJ - Do not listen for specific range.
                 * - Detect current orientation
                 * - Keep that orientation until orientation reach almost orientation change
                 * - Update orientatation and again keep orientation until device reach the other orientation
                 *
                 * - Also keep in mind it's not about UX (switching icons) but also set proper orientation to the saved image!!!
                 */
                when (orientation) {
                    in 0..90 -> Timber.d(">>>_ orientation PORTRAIT $orientation")
                    in 91..180 -> Timber.d(">>>_ orientation LANDSCAPE_REV $orientation")
                    in 181..270 -> Timber.d(">>>_ orientation PORTRAIT_REV $orientation")
                    in 271..360 -> Timber.d(">>>_ orientation LANDSCAPE $orientation")
                }

            }

        }


        setContent {
            KotoxBasicTheme() {
                CameraScreenContent(
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
                            //Event is processed on the CameraCapture level only.
                        }
                    }.exhaustive
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
