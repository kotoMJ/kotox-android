package cz.kotox.media.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.compose.runtime.remember
import cz.kotox.common.core.android.extension.collectAsStateWithLifecycle
import cz.kotox.common.camera.custom.CameraCustomActivityLauncher
import cz.kotox.common.ui.theme.KotoxBasicTheme
import cz.kotox.media.ui.main.MainScreen
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

internal interface MainActivityListener {
    fun onStartCustomCamera()
}

@AndroidEntryPoint
class MainActivity : ComponentActivity(), CameraCustomActivityLauncher {

    private val viewModel: MainActivityViewModel by viewModels()

    override val baseActivity: ComponentActivity = this

    override val cameraActivityLauncher: ActivityResultLauncher<Intent> = createCameraLauncher()

    override fun onPhoto(photoUri: Uri) {
        Timber.d(">>>_ activity captured uri: $photoUri")
        viewModel.updatePhotoUri(photoUri = photoUri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            KotoxBasicTheme {

                val listener = remember {
                    object : MainActivityListener {
                        override fun onStartCustomCamera() {
                            launchCustomCamera()
                        }

                    }
                }

                val state = viewModel.state.collectAsStateWithLifecycle()

                MainScreen(
                    state = state,
                    listener = listener
                )
            }
        }
    }
}
