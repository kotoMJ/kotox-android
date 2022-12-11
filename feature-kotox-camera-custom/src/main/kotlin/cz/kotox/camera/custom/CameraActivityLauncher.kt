package cz.kotox.camera.custom

import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity

internal const val ARG_OUT_PHOTO_URI = "ARG_OUT_PHOTO_URI"

interface CameraActivityLauncher {

    val cameraActivityLauncher: ActivityResultLauncher<Intent>

    val baseActivity: AppCompatActivity

    fun launchCamera() {
        cameraActivityLauncher.launch(
            CameraActivityIntentUtil.getCameraStartIntent(baseActivity)
        )
    }

    fun onPhoto(photoUri: Uri)
}