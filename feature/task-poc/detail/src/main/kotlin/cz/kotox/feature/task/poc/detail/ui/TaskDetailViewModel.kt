package cz.kotox.feature.task.poc.detail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.kotox.common.task.poc.ui.TaskSummaryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val ARG_TASK_ID = "arg_task_id"

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val getOneTasksUseCase: cz.kotox.common.task.poc.domain.usecase.GetOneTaskUseCase,
    private val getOneTaskImageUseCase: cz.kotox.common.task.poc.domain.usecase.GetOneTaskImageUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<TaskSummaryItem?>(null)
    val uiState: StateFlow<TaskSummaryItem?> = _uiState

    private val _ongoingDownload = MutableStateFlow<Boolean>(false)
    val ongoingDownload: StateFlow<Boolean> = _ongoingDownload

    //
    private val taskId: String =
        requireNotNull(savedStateHandle[ARG_TASK_ID]) { "Task ID NOT set!" }

    init {
        viewModelScope.launch {
            loadTask()
        }
    }

    suspend fun loadTask() {
        val task = getOneTasksUseCase.execute(taskId = taskId)
        val taskImage = getOneTaskImageUseCase.execute(taskId = taskId)
        _uiState.update { TaskSummaryItem.from(task).copy(localImageUrl = taskImage) }
    }

    fun setDownloadStarted() {
        _ongoingDownload.update { true }
    }

    fun setDownloadFinished() {
        _ongoingDownload.update { false }
    }
}
