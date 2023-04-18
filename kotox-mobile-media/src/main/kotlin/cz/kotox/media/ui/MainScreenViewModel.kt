package cz.kotox.media.ui

import android.net.Uri
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

//    private val latestPhotoUri: MutableState<Uri?> = mutableStateOf(null)
//
//    val latestPhotoUriPresenter = derivedStateOf { latestPhotoUri.value }
//
//    fun updateLatestPhotoUri(photoUri: Uri?) {
//        latestPhotoUri.value = photoUri
//    }

    fun updatePhotoUri(photoUri: Uri?) =
        _state.update { it.copy(latestPhotoUri = photoUri) }

    private fun updateColors(rgbColors: List<Int>) =
        _state.update { it.copy(significantPhotoUriRgbColors = rgbColors) }
}
