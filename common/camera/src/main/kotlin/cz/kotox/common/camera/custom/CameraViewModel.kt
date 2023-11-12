package cz.kotox.common.camera.custom

import androidx.camera.core.CameraSelector
import androidx.camera.core.ZoomState
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import cz.kotox.common.camera.custom.capture.ZoomValues
import cz.kotox.common.core.extension.roundOneDecimal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import javax.inject.Inject

enum class LensFacing {
    BACK,
    FRONT,
    NOT_DETECTED
}

@HiltViewModel
@ExperimentalCoroutinesApi
class CameraActivityViewModel @Inject constructor() : ViewModel() {

    private val availableCameraSelectors: MutableState<List<LensFacing>> = mutableStateOf(emptyList())

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
                val currentSelectorIndex = availableSelectorsList.indexOf(currentCameraSelector.value)
                availableSelectorsList.getOrElse(currentSelectorIndex + 1) { availableSelectorsList.firstOrNull() }
            }
            currentCameraSelector.value = nextSelector
        }
    }

}
