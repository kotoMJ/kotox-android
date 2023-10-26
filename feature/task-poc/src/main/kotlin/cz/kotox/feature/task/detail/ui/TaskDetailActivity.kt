package cz.kotox.feature.task.detail.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import cz.kotox.android.feature.task.detail.R
import cz.kotox.core.ui.theme.KotoxBasicTheme
import cz.kotox.task.domain.api.usecase.DownloadTaskPhotoUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TaskDetailActivity : ComponentActivity() {

    private val viewModel: TaskDetailViewModel by viewModels()

    @Inject
    lateinit var taskPhotoUseCase: DownloadTaskPhotoUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotoxBasicTheme {
                TaskDetailScreen(
                    input = TaskDetailScreenInput(
                        viewModel.uiState.collectAsState().value,
                        viewModel.ongoingDownload.collectAsState().value
                    ),
                    onEventHandler = { event ->
                        when (event) {
                            TaskDetailScreenEvent.ExitScreen -> {
                                finish()
                            }

                            is TaskDetailScreenEvent.DownloadImage -> {
                                viewModel.viewModelScope.launch {
                                    viewModel.viewModelScope.launch {
                                        viewModel.setDownloadStarted()
                                    }
                                    val success = taskPhotoUseCase.execute(
                                        this@TaskDetailActivity,
                                        event.taskId
                                    )
                                    if (success) {
                                        viewModel.viewModelScope.launch {
                                            viewModel.loadTask()
                                            viewModel.setDownloadFinished()
                                        }
                                    } else {
                                        //We currently use toast as the simplest messaging solution.
                                        if (!this@TaskDetailActivity.isDestroyed && !this@TaskDetailActivity.isFinishing) {
                                            Toast.makeText(
                                                this@TaskDetailActivity,
                                                this@TaskDetailActivity.getString(R.string.task_detail_image_download_failed),
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                        viewModel.viewModelScope.launch {
                                            viewModel.setDownloadFinished()
                                        }
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}
