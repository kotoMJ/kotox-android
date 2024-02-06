package cz.kotox.common.camera.custom

import android.content.Context
import android.content.Intent

object CameraCustomActivityIntentUtil {

    fun getCameraStartIntent(
        context: Context,
        layout: CameraCustomLayout
    ): Intent {
        val clazz = CameraCustomActivity::class.java
        return Intent(context.applicationContext, clazz).apply {
            putExtra(ARG_CAMERA_LAYOUT, layout)
        }
    }
}
