package cz.kotox.task.list.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class MainViewModel2 @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainFeedState())
    val uiState: StateFlow<MainFeedState> = _uiState

    private val _remoteRefreshOngoing = MutableStateFlow(false)
    val remoteRefreshOngoing: StateFlow<Boolean> = _remoteRefreshOngoing

}