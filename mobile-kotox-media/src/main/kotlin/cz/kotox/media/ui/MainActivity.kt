package cz.kotox.media.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import cz.kotox.camera.custom.CameraCustomActivityLauncher
import cz.kotox.core.ui.theme.KotoxBasicTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity(), CameraCustomActivityLauncher {

    private val viewModel: MainViewModel by viewModels()

    override val baseActivity: ComponentActivity = this

    override val cameraActivityLauncher: ActivityResultLauncher<Intent> = createCameraLauncher()

    override fun onPhoto(photoUri: Uri) {
        Timber.d(">>>_ captured uri: $photoUri")
        viewModel.updateLatestPhotoUri(photoUri = photoUri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            KotoxBasicTheme {
                MainScreen(
                    input = MainScreenInput(viewModel.latestPhotoUriPresenter.value),
                    onEventHandler = { event ->
                        when (event) {
                            MainScreenEvent.StartCustomCamera -> {
                                launchCustomCamera()
                            }
                        }
                    }
                )
            }
        }
    }
}
