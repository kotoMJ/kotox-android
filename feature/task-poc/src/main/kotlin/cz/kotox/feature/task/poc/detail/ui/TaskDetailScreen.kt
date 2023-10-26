package cz.kotox.feature.task.poc.detail.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cz.kotox.android.feature.task.detail.R
import cz.kotox.common.task.poc.ui.TaskSummaryComponentInput
import cz.kotox.common.task.poc.ui.TaskSummaryItem
import cz.kotox.core.ui.theme.LocalColors
import cz.kotox.core.ui.theme.LocalTypography
import java.time.LocalDateTime

const val UI_TEST_DOWNLOAD_BUTTON_TAG = "TestTagDownloadButton"

sealed class TaskDetailScreenEvent {
    object ExitScreen : TaskDetailScreenEvent()
    data class DownloadImage(val taskId: String) : TaskDetailScreenEvent()
}

data class TaskDetailScreenInput(
    val taskItem: TaskSummaryItem?,
    val onGoingDownload: Boolean = false
)

//TODO: Comment out text marked with TODO to see the preview, there is some issue with
// preview text inside the button
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Light Mode"
)
@Preview(
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Light Mode",

    )
@Composable
fun TaskDetailScreen(
    @PreviewParameter(MainScreenPreviewProvider::class) input: TaskDetailScreenInput,
    onEventHandler: (TaskDetailScreenEvent) -> Unit = {}
) {

    Scaffold(
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            Column {
                MainScreenAppBarView(
                    onEventHandler = { event ->
                        when (event) {
                            TaskDetailAppBarEvent.AppBarHomeEvent -> {
                                onEventHandler.invoke(TaskDetailScreenEvent.ExitScreen)
                            }
                        }
                    },
                    input = TaskDetailAppBarInput(input.taskItem?.title ?: "")
                )
            }
        },
        content = { _ ->

            if (input.taskItem == null) {
                CircularProgressIndicator()
            } else {
                if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    Row() {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                        ) {
                            Box() {
                                Box(
                                    modifier = Modifier
                                        .width(360.dp)
                                        .fillMaxHeight()
                                        .background(LocalColors.current.divider)
                                )
                                AsyncImage(
                                    model = input.taskItem.localImageUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(360.dp),
                                    contentScale = ContentScale.Crop,
                                )
                            }
                        }

                        Column(
                            modifier = Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 24.dp
                            )
                        ) {
                            TaskDetailItem(
                                input = TaskSummaryComponentInput(
                                    item = input.taskItem,
                                    useHeadlineTitle = false
                                )
                            )
                            AnimatedVisibility(visible = input.taskItem.localImageUrl == null) {
                                TextButton(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag(
                                            UI_TEST_DOWNLOAD_BUTTON_TAG
                                        ),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                                    enabled = !input.onGoingDownload,
                                    onClick = {
                                        onEventHandler.invoke(
                                            TaskDetailScreenEvent.DownloadImage(
                                                input.taskItem.id
                                            )
                                        )
                                    }
                                ) {
                                    if (input.onGoingDownload) {
                                        CircularProgressIndicator()
                                    } else {
                                        Text(
                                            text = stringResource(id = R.string.task_detail_button_download_label),
                                            style = LocalTypography.current.body1Regular,
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Column() {
                        Box() {
                            Box(
                                modifier = Modifier
                                    .height(200.dp)
                                    .fillMaxWidth()
                                    .background(LocalColors.current.divider)
                            )
                            AsyncImage(
                                model = input.taskItem.localImageUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .height(200.dp),
                                contentScale = ContentScale.Crop,
                            )
                        }
                        TaskDetailItem(
                            input = TaskSummaryComponentInput(
                                item = input.taskItem,
                                useHeadlineTitle = false
                            )
                        )
                    }
                    AnimatedVisibility(visible = input.taskItem.localImageUrl == null) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp, vertical = 24.dp),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            TextButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag(
                                        UI_TEST_DOWNLOAD_BUTTON_TAG
                                    ),
                                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                                enabled = !input.onGoingDownload,
                                onClick = {
                                    onEventHandler.invoke(
                                        TaskDetailScreenEvent.DownloadImage(
                                            input.taskItem.id
                                        )
                                    )
                                }
                            ) {
                                if (input.onGoingDownload) {
                                    CircularProgressIndicator()
                                } else {
                                    Text(
                                        text = stringResource(id = R.string.task_detail_button_download_label),
                                        style = LocalTypography.current.body1Regular,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}


class MainScreenPreviewProvider : PreviewParameterProvider<TaskDetailScreenInput> {
    override val values: Sequence<TaskDetailScreenInput> = sequenceOf(
        TaskDetailScreenInput(
            taskItem =
            TaskSummaryItem.from(
                cz.kotox.common.task.poc.domain.model.Task(
                    creationDate = LocalDateTime.now(),
                    dueDate = LocalDateTime.now().plusDays(2),
                    id = "1",
                    description = "Some description",
                    title = "Some title",
                    image = "https://fastly.picsum.photos/id/25/5000/3333.jpg?hmac=yCz9LeSs-i72Ru0YvvpsoECnCTxZjzGde805gWrAHkM"
                )
            )
        )
    )
}