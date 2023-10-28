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
internal data class MainActivityViewState(
    val latestPhotoUri: Uri? = null,
)

@HiltViewModel
internal class MainActivityViewModel @Inject constructor(
) : ViewModel() {

    private val _state: MutableStateFlow<MainActivityViewState> =
        MutableStateFlow(MainActivityViewState())
    val state: StateFlow<MainActivityViewState> = _state.asStateFlow()

    fun updatePhotoUri(photoUri: Uri) =
        _state.update { it.copy(latestPhotoUri = photoUri) }

}
