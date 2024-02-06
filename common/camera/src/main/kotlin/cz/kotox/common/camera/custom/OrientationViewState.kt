package cz.kotox.common.camera.custom

import androidx.compose.runtime.Immutable
import kotlin.math.absoluteValue

@Immutable
data class OrientationViewState(
    val rotationDegree: Int,
    val directionClockwise: Boolean
) {

    // FIXME MJ - this logic is flaky .... fix transitions
    @Suppress("MagicNumber")
    val cameraOrientation: CameraOrientation = when (directionClockwise) {
        true -> {
            when (rotationDegree.absoluteValue) {
                in 0..75 -> CameraOrientation.PORTRAIT
                in 76..165 -> CameraOrientation.LANDSCAPE
                in 166..255 -> CameraOrientation.PORTRAIT_REV
                in 256..285 -> CameraOrientation.LANDSCAPE_REV
                in 286..359 -> CameraOrientation.PORTRAIT
                else -> throw IllegalArgumentException(
                    "Illegal degree for rotation (clockwise): $rotationDegree"
                )
            }
        }

        false -> {
            when (rotationDegree.absoluteValue) {
                in 0..15 -> CameraOrientation.PORTRAIT
                in 16..105 -> CameraOrientation.LANDSCAPE
                in 106..195 -> CameraOrientation.PORTRAIT_REV
                in 196..285 -> CameraOrientation.LANDSCAPE_REV
                in 286..359 -> CameraOrientation.PORTRAIT
                else -> throw IllegalArgumentException(
                    "Illegal degree for rotation (antiClockwise): $rotationDegree"
                )
            }
        }
    }
}

//        when (rotationInDegree) {
//            in 0..90 -> Timber.d(">>>_ orientation PORTRAIT $rotationInDegree")
//            in 91..180 -> Timber.d(">>>_ orientation LANDSCAPE_REV $rotationInDegree")
//            in 181..270 -> Timber.d(">>>_ orientation PORTRAIT_REV $rotationInDegree")
//            in 271..360 -> Timber.d(">>>_ orientation LANDSCAPE $rotationInDegree")
//        }
