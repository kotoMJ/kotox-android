package cz.kotox.media.ui

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {

    private val latestPhotoUri: MutableState<Uri?> = mutableStateOf(null)

    val latestPhotoUriPresenter = derivedStateOf { latestPhotoUri.value }

    fun updateLatestPhotoUri(photoUri: Uri?) {
        latestPhotoUri.value = photoUri
    }

}
