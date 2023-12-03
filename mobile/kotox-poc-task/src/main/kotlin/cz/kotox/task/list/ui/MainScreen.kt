package cz.kotox.task.list.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.layoutId
import cz.kotox.common.designsystem.theme.LocalColors
import cz.kotox.feature.task.poc.detail.ui.TaskDetailActivityIntentUtil
import cz.kotox.task.R
import cz.kotox.common.task.poc.R as CTPR
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import cz.kotox.common.task.poc.ui.TaskSummaryComponentInput
import cz.kotox.common.task.poc.ui.TaskSummaryItem
import java.time.LocalDateTime

const val UI_TEST_MAIN_LIST_TAG = "MainListTag"

sealed class MainScreenEvent {
    object RequestDownload : MainScreenEvent()
}

data class MainScreenInput(
    val mainFeedState: MainFeedState,
    val isRefreshing: Boolean = false,
)

//Night mode @Preview does not work for scaffold correctly due to current solution in ThemeUtils
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPagerApi::class)
@Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Light Mode"
)
@Suppress("All JetPack Compose previews contain 'Preview' in method name")
@Composable
fun MainScreen(
    @PreviewParameter(MainScreenPreviewProvider::class) input: MainScreenInput,
    onEventHandler: (MainScreenEvent) -> Unit = {}
) {

    val pagerState = rememberPagerState(0)
    val context = LocalContext.current

    Scaffold(
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            Column {
                MainScreenAppBarView(
                    onEventHandler = { event ->
                    },
                    input = MainScreenAppBarInput(stringResource(id = R.string.main_screen_title))
                )

                MainScreenSectionTabRow(
                    input = MainScreenTypeTabInput(
                        pagerState = pagerState,
                        taskGroups = input.mainFeedState.taskGroups
                    ),
                    onEventHandler = { }
                )

            }
        },
        content = { _ ->
            HorizontalPager(
                count = input.mainFeedState.taskGroups.size,
                state = pagerState,
                verticalAlignment = Alignment.Top, //Avoid centering content by default
            ) { page ->


                SwipeRefresh(
                    state = rememberSwipeRefreshState(input.isRefreshing),
                    onRefresh = { onEventHandler.invoke(MainScreenEvent.RequestDownload) },
                ) {
                    if (input.mainFeedState.taskGroups[page].tasks.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            IconButton(
                                modifier = Modifier.align(alignment = Alignment.Center),
                                onClick = { onEventHandler.invoke(MainScreenEvent.RequestDownload) }
                            ) {
                                Icon(
                                    modifier = Modifier.layoutId("createdIcon"),
                                    painter = painterResource(
                                        id = CTPR.drawable.ic_refresh
                                    ),
                                    contentDescription = stringResource(id = R.string.generic_click_to_refresh),
                                    tint = cz.kotox.common.designsystem.theme.LocalColors.current.primary,
                                )
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.testTag(UI_TEST_MAIN_LIST_TAG + page),
                            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            itemsIndexed(
                                items = input.mainFeedState.taskGroups[page].tasks,
                                itemContent = { index, task ->
                                    MainScreenTaskItem(
                                        modifier = Modifier.clickable {
                                            context.startActivity(
                                                TaskDetailActivityIntentUtil.getCameraStartIntent(
                                                    context = context,
                                                    taskId = task.id
                                                )
                                            )
                                        },
                                        input = TaskSummaryComponentInput(
                                            item = task,
                                            useHeadlineTitle = false
                                        )
                                    )
                                    if (index < input.mainFeedState.taskGroups[page].tasks.lastIndex)
                                        Divider(
                                            color = cz.kotox.common.designsystem.theme.LocalColors.current.divider,
                                            thickness = 1.dp
                                        )
                                }
                            )
                        }
                    }
                }
            }

        }
    )

}

@Composable
fun TaskGroup.getTitleString(): String =
    "${stringResource(id = this.titleResId)} (${this.tasks.size})"


class MainScreenPreviewProvider : PreviewParameterProvider<MainScreenInput> {
    override val values: Sequence<MainScreenInput> = sequenceOf(
        MainScreenInput(
            mainFeedState = MainFeedState(
                isInitialLoading = false,
                taskGroups = listOf(
                    TaskGroup(
                        tasks = listOf(
                            TaskSummaryItem.from(
                                cz.kotox.common.task.poc.domain.model.Task(
                                    id = "1",
                                    creationDate = LocalDateTime.now(),
                                    dueDate = LocalDateTime.now().plusDays(2),
                                    description = "Description",
                                    title = "Title",
                                    image = "https://i.imgur.com/GTag1cl.png"
                                )
                            ),
                            TaskSummaryItem.from(
                                cz.kotox.common.task.poc.domain.model.Task(
                                    id = "2",
                                    creationDate = LocalDateTime.now(),
                                    dueDate = LocalDateTime.now().plusDays(2),
                                    description = "Description",
                                    title = "Title",
                                    image = "https://i.imgur.com/GTag1cl.png"
                                )
                            ),
                            TaskSummaryItem.from(
                                cz.kotox.common.task.poc.domain.model.Task(
                                    id = "3",
                                    creationDate = LocalDateTime.now(),
                                    dueDate = LocalDateTime.now().plusDays(2),
                                    description = "Description",
                                    title = "Title",
                                    image = "https://i.imgur.com/GTag1cl.png"
                                )
                            ),
                            TaskSummaryItem.from(
                                cz.kotox.common.task.poc.domain.model.Task(
                                    id = "4",
                                    creationDate = LocalDateTime.now(),
                                    dueDate = LocalDateTime.now().plusDays(2),
                                    description = "Description",
                                    title = "Title",
                                    image = "https://i.imgur.com/GTag1cl.png"
                                )
                            ),
                        ), titleResId = R.string.main_screen_section_title_all
                    ),
                    TaskGroup(
                        tasks = listOf(
                            TaskSummaryItem.from(
                                cz.kotox.common.task.poc.domain.model.Task(
                                    id = "11",
                                    creationDate = LocalDateTime.now(),
                                    dueDate = LocalDateTime.now().plusDays(2),
                                    description = "Description",
                                    title = "Title",
                                    image = "https://i.imgur.com/GTag1cl.png"
                                )
                            ),
                            TaskSummaryItem.from(
                                cz.kotox.common.task.poc.domain.model.Task(
                                    id = "12",
                                    creationDate = LocalDateTime.now(),
                                    dueDate = LocalDateTime.now().plusDays(2),
                                    description = "Description",
                                    title = "Title",
                                    image = "https://i.imgur.com/GTag1cl.png"
                                )
                            ),
                        ), titleResId = R.string.main_screen_section_title_upcoming
                    )
                )
            )
        )
    )
}