package cz.kotox.common.camera.custom

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

@Suppress("FunctionNaming")
internal fun CameraScreenPresenter(
    rotationDegreeFlow: Flow<Int>,
    directionClockwiseFlow: Flow<Boolean>
) = combine(rotationDegreeFlow, directionClockwiseFlow) { rotationDegree, direction ->
    CameraViewState(
        rotationDegree = rotationDegree,
        directionClockwise = direction
    )
}
