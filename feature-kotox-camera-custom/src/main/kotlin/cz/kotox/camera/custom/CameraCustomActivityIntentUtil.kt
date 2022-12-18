package cz.kotox.camera.custom

import android.content.Context
import android.content.Intent

object CameraCustomActivityIntentUtil {

    fun getCameraStartIntent(
        context: Context,
    ): Intent {
        val clazz = CameraCustomActivity::class.java
        return Intent(context.applicationContext, clazz)
    }

}