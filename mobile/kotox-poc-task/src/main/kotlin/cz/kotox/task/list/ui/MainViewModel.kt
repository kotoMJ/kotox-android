package cz.kotox.task.list.ui

import androidx.lifecycle.ViewModel
import cz.kotox.common.core.error.BasicError
import cz.kotox.common.task.poc.domain.usecase.GetAllTasksUseCase
import cz.kotox.task.R
import cz.kotox.common.task.poc.ui.TaskSummaryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime
import javax.inject.Inject

data class TaskGroup(
    val tasks: List<TaskSummaryItem> = emptyList(),
    val titleResId: Int
)

data class MainFeedState(
    val isInitialLoading: Boolean = true,
    val taskGroups: List<TaskGroup> = createTaskGroups(),
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val getOneTaskImageUseCase: cz.kotox.common.task.poc.domain.usecase.GetOneTaskImageUseCase,
    private val remoteRefreshTasksUseCase: cz.kotox.common.task.poc.domain.usecase.RefreshTasksUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainFeedState())
    val uiState: StateFlow<MainFeedState> = _uiState

    private val _remoteRefreshOngoing = MutableStateFlow(false)
    val remoteRefreshOngoing: StateFlow<Boolean> = _remoteRefreshOngoing

    suspend fun loadData() {
        getAllTasksUseCase.execute().collect { tasks ->
            _uiState.update { uiState ->
                val allTasks = tasks
                    .map(TaskSummaryItem::from)
                    .map {
                        //TODO I understand this is not optimal in terms of performance, but works for now
                        it.copy(localImageUrl = getOneTaskImageUseCase.execute(it.id))
                    }
                    .sortedBy { it.creationDate }
                val upcomingTasks = allTasks.filter { it.dueDate.isAfter(LocalDateTime.now()) }
                    .sortedBy { it.dueDate }
                uiState.copy(
                    isInitialLoading = false,
                    taskGroups = createTaskGroups(allTasks, upcomingTasks)
                )
            }
        }
    }

    suspend fun triggerRemoteRefresh(onErrorMessage: (error: BasicError) -> Unit) {
        _remoteRefreshOngoing.update { true }
        remoteRefreshTasksUseCase.execute(
            {
                _remoteRefreshOngoing.update { false }
                onErrorMessage.invoke(it)
            },
            {
                _remoteRefreshOngoing.update { false }
            }
        )
    }
}

private fun createTaskGroups(
    all: List<TaskSummaryItem> = emptyList(),
    upcoming: List<TaskSummaryItem> = emptyList()
): List<TaskGroup> = listOf(
    TaskGroup(all, R.string.main_screen_section_title_all),
    TaskGroup(upcoming, R.string.main_screen_section_title_upcoming)
)