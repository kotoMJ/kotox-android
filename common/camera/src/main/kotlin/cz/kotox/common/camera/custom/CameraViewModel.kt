package cz.kotox.common.camera.custom

import androidx.camera.core.CameraSelector
import androidx.camera.core.ZoomState
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.kotox.common.camera.custom.capture.ZoomValues
import cz.kotox.common.core.android.extension.stateInForScope
import cz.kotox.common.core.android.flow.SaveableMutableSaveStateFlow
import cz.kotox.common.core.extension.roundOneDecimal
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

enum class LensFacing {
    BACK,
    FRONT,
    NOT_DETECTED
}

private const val PORTRAIT_STABLE_ROTATION_ANGLE = 0f
private const val LANDSCAPE_STABLE_ROTATION_ANGLE = -90f
private const val PORTRAIT_REV_STABLE_ROTATION_ANGLE = -180f
private const val LANDSCAPE_REV_STABLE_ROTATION_ANGLE = -270f

enum class CameraOrientation(val rotation: Float) {
    PORTRAIT(PORTRAIT_STABLE_ROTATION_ANGLE),
    LANDSCAPE(LANDSCAPE_STABLE_ROTATION_ANGLE),
    PORTRAIT_REV(PORTRAIT_REV_STABLE_ROTATION_ANGLE),
    LANDSCAPE_REV(LANDSCAPE_REV_STABLE_ROTATION_ANGLE)
}

@HiltViewModel
@ExperimentalCoroutinesApi
class CameraActivityViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val availableCameraSelectors: MutableState<List<LensFacing>> = mutableStateOf(
        emptyList()
    )

    private val currentCameraSelector: MutableState<LensFacing?> = mutableStateOf(null)

    val currentCameraSelectorPresenter = derivedStateOf { currentCameraSelector.value }

    private val currentZoomValues: MutableState<ZoomValues?> = mutableStateOf(null)

    val currentZoomValuesPresenter = derivedStateOf { currentZoomValues.value }

    val zoomStateObserver: Observer<ZoomState> = Observer { state: ZoomState ->
        currentZoomValues.value = ZoomValues(
            minRatio = state.minZoomRatio.roundOneDecimal(),
            maxRatio = state.maxZoomRatio.roundOneDecimal(),
            currentRatio = state.zoomRatio.roundOneDecimal(),
            currentLinearZoom = state.linearZoom
        )
        Timber.d(">>>_ ZOOM state change! ${currentZoomValues.value}")
    }

    private val rotationDegree = SaveableMutableSaveStateFlow(
        savedStateHandle = savedStateHandle,
        key = "rotationDegree",
        defaultValue = 0
    )
    private val directionClockwise = SaveableMutableSaveStateFlow(
        savedStateHandle = savedStateHandle,
        key = "directionClockwise",
        defaultValue = true
    )

    val orientationViewState: StateFlow<OrientationViewState> = OrientationViewStatePresenter(
        rotationDegreeFlow = rotationDegree.state,
        directionClockwiseFlow = directionClockwise.state
    ).stateInForScope(
        scope = viewModelScope,
        initialValue = OrientationViewState(0, true)
    )

    init {
        Timber.d("create CameraActivityViewModel")
    }

    fun setAvailableCameraSelectors(cameraProvider: ProcessCameraProvider) {
        if (availableCameraSelectors.value.isEmpty()) {
            val availableSelectorList = mutableListOf<LensFacing>()
            if (cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA)) {
                availableSelectorList.add(LensFacing.BACK)
            }
            if (cameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA)) {
                availableSelectorList.add(LensFacing.FRONT)
            }

            if (availableSelectorList.isEmpty()) {
                availableSelectorList.add(LensFacing.NOT_DETECTED)
            }

            Timber.d(">>>_ CAMERA, available selectorList size: ${availableSelectorList.size} ")

            availableCameraSelectors.value = availableSelectorList.toList()
            setNextCameraSelector()
        }
    }

    fun setNextCameraSelector() {
        val availableSelectorsList = availableCameraSelectors.value

        if (availableSelectorsList.contains(LensFacing.NOT_DETECTED)) {
            currentCameraSelector.value = LensFacing.NOT_DETECTED
        } else {
            val nextSelector: LensFacing? = if (currentCameraSelector.value == null) {
                availableSelectorsList.firstOrNull()
            } else {
                val currentSelectorIndex = availableSelectorsList.indexOf(
                    currentCameraSelector.value
                )
                availableSelectorsList.getOrElse(currentSelectorIndex + 1) { availableSelectorsList.firstOrNull() }
            }
            currentCameraSelector.value = nextSelector
        }
    }

    fun setCurrentCameraRotation(newRotationDegree: Int) {
        directionClockwise.value = ((newRotationDegree - rotationDegree.value) > 0)
        rotationDegree.value = newRotationDegree

        /**
         * FIXME MJ - Do not listen for specific range.
         * - Detect current orientation
         * - Keep that orientation until orientation reach almost orientation change
         * - Update orientatation and again keep orientation until device reach the other orientation
         *
         * - Also keep in mind it's not about UX (switching icons) but also set proper orientation to the saved image!!!
         */
//        when (rotationInDegree) {
//            in 0..90 -> Timber.d(">>>_ orientation PORTRAIT $rotationInDegree")
//            in 91..180 -> Timber.d(">>>_ orientation LANDSCAPE_REV $rotationInDegree")
//            in 181..270 -> Timber.d(">>>_ orientation PORTRAIT_REV $rotationInDegree")
//            in 271..360 -> Timber.d(">>>_ orientation LANDSCAPE $rotationInDegree")
//        }
    }
}
