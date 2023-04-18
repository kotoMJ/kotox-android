package cz.kotox.media.ui

import android.net.Uri
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject


@Immutable
internal data class MainScreenViewState(
    val latestPhotoUri: Uri? = null,
    val significantPhotoUriRgbColors: List<Int> = emptyList()
)

@HiltViewModel
internal class MainScreenViewModel @Inject constructor(
) : ViewModel() {

    private val _state: MutableStateFlow<MainScreenViewState> = MutableStateFlow(MainScreenViewState())
    val state: StateFlow<MainScreenViewState> = _state.asStateFlow()

    fun updatePhotoUri(photoUri: Uri?) {
        _state.update { it.copy(latestPhotoUri = photoUri) }
    }
    fun updateColors(rgbColors: List<Int>) {
        Timber.d(">>>_ Update colors to: $rgbColors")
        _state.update { it.copy(significantPhotoUriRgbColors = rgbColors) }
    }
}
