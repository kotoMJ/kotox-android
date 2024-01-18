package cz.kotox.common.camera.custom

import androidx.compose.runtime.Immutable

@Immutable
internal data class CameraViewState(
    val rotationDegree: Int,
    val directionClockwise: Boolean
)
