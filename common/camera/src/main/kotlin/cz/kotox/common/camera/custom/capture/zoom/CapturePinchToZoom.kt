package cz.kotox.common.camera.custom.capture.zoom

import android.content.Context
import android.view.ScaleGestureDetector
import androidx.camera.core.Camera

fun createScaleGestureDetector(
    context: Context,
    camera: Camera,
    onGestureDetected: () -> Unit = {}
): ScaleGestureDetector {
//    val context = LocalContext.current

    // Create a listener with a callback invoked when a gesture event has occurred
    val listener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            // Get the current camera zoom ratio
            val currentZoomRatio: Float = camera.cameraInfo.zoomState.value?.zoomRatio ?: 1F

            // Get by how much the scale has changed due to the user's pinch gesture
            val delta = detector.scaleFactor

            onGestureDetected.invoke()

            // Update the camera's zoom ratio
            camera.cameraControl.setZoomRatio(currentZoomRatio * delta)
            return true
        }
    }

    // Attach PreviewView's touch events listener to the scale gesture listener
    return ScaleGestureDetector(context, listener)
}
