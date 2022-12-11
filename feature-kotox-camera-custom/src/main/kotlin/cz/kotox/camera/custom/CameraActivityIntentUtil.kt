package cz.kotox.camera.custom

import android.content.Context
import android.content.Intent

object CameraActivityIntentUtil {

    fun getCameraStartIntent(
        context: Context,
    ): Intent {
        val clazz = CameraActivity::class.java
        return Intent(context.applicationContext, clazz)
    }

}