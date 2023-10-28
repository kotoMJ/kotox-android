package cz.kotox.common.camera.custom

import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import timber.log.Timber

internal const val ARG_OUT_PHOTO_URI = "ARG_OUT_PHOTO_URI"

interface CameraCustomActivityLauncher {

    val cameraActivityLauncher: ActivityResultLauncher<Intent>

    val baseActivity: ComponentActivity

    fun launchCustomCamera() {
        cameraActivityLauncher.launch(
            CameraCustomActivityIntentUtil.getCameraStartIntent(baseActivity)
        )
    }

    fun createCameraLauncher(): ActivityResultLauncher<Intent> =
        baseActivity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            val photoUri = it.data?.getParcelableExtra<Uri>(ARG_OUT_PHOTO_URI)
            Timber.d("Photo uri: $photoUri")
            if (photoUri != null) {
                onPhoto(photoUri)
            }
        }

    fun onPhoto(photoUri: Uri)
}