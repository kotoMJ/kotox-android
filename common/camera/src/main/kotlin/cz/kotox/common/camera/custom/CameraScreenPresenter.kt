package cz.kotox.common.camera.custom

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

@Suppress("FunctionNaming")
internal fun OrientationViewStatePresenter(
    rotationDegreeFlow: Flow<Int>,
    directionClockwiseFlow: Flow<Boolean>
) = combine(rotationDegreeFlow, directionClockwiseFlow) { rotationDegree, direction ->
    OrientationViewState(
        rotationDegree = rotationDegree,
        directionClockwise = direction
    )
}
