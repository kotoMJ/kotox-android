package cz.kotox.camera.custom

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.camera.core.CameraInfoUnavailableException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import cz.kotox.camera.custom.capture.CameraScreenContent
import cz.kotox.camera.custom.capture.CameraScreenEvent
import cz.kotox.camera.custom.capture.CameraScreenInput
import com.google.common.util.concurrent.ListenableFuture
import cz.kotox.android.core.extensions.exhaustive
import cz.kotox.core.ui.theme.KotoxBasicTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

val EMPTY_IMAGE_URI: Uri = Uri.parse("file://dev/null")

@OptIn(
    ExperimentalCoroutinesApi::class,
)
@AndroidEntryPoint
class CameraCustomActivity : ComponentActivity() {

    val viewModel: CameraActivityViewModel by viewModels()

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

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

        setContent {
            KotoxBasicTheme() {
                CameraScreenContent(
                    input = CameraScreenInput(
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