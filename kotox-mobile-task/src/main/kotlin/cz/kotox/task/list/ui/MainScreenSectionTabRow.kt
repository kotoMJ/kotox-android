package cz.kotox.task.list.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cz.kotox.core.ui.theme.LocalColors
import cz.kotox.core.ui.theme.LocalTypography
import cz.kotox.core.ui.theme.KotoxBasicTheme
import cz.kotox.task.domain.api.factory.toTask
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import cz.kotox.android.task.R
import cz.kotox.common.task.poc.ui.TaskSummaryItem
import kotlinx.coroutines.launch
import java.time.LocalDateTime

const val UI_TEST_MAIN_TAB_TAG = "MainTab"
sealed class MainScreenSectionTabEvent {
    data class MainScreenSectionSelected(val sectionResId: Int) :
        MainScreenSectionTabEvent()
}

data class MainScreenTypeTabInput @ExperimentalPagerApi constructor(
    val pagerState: PagerState,
    val taskGroups: List<TaskGroup>,
)

@Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Light Mode"
)
@Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@ExperimentalPagerApi
@Composable
fun MainScreenSectionTabRow(
    @PreviewParameter(MainScreenTypeTabPreviewProvider::class) input: MainScreenTypeTabInput,
    onEventHandler: (MainScreenSectionTabEvent) -> Unit = {},
) {

    KotoxBasicTheme {

        val selectedTabIndex by derivedStateOf { input.pagerState.currentPage }


        TabRow(
            modifier = Modifier.zIndex(2f),
            selectedTabIndex = selectedTabIndex,
            backgroundColor = MaterialTheme.colors.surface,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    //color = MaterialTheme.colors.linkTextColor,
                    height = 2.dp
                )
            },
            //divider = { TabRowDefaults.Divider(Modifier.wrapContentSize(Alignment.BottomStart)) }
        ) {
            input.taskGroups.forEachIndexed { index, taskGroup ->
                val scrollScope = rememberCoroutineScope()
                Tab(
                    modifier = Modifier.zIndex(1f),
                    selected = selectedTabIndex == index,
                    onClick = {
                        scrollScope.launch { input.pagerState.animateScrollToPage(index) }
                        onEventHandler(
                            MainScreenSectionTabEvent.MainScreenSectionSelected(
                                taskGroup.titleResId
                            )
                        )
                    },
                    selectedContentColor = LocalColors.current.onControlsPrimary,
                    unselectedContentColor = LocalColors.current.onControlsSecondary,
                ) {

                    Box(modifier = Modifier.padding(vertical = 4.dp)) {
                        Text(
                            modifier = Modifier.padding(12.dp).testTag(UI_TEST_MAIN_TAB_TAG),
                            text = taskGroup.getTitleString(),
                            style = LocalTypography.current.body2Medium,
                        )
                    }
                }

            }
        }
    }
}

class MainScreenTypeTabPreviewProvider : PreviewParameterProvider<MainScreenTypeTabInput> {
    @OptIn(ExperimentalPagerApi::class)
    override val values: Sequence<MainScreenTypeTabInput> = sequenceOf(
        MainScreenTypeTabInput(
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
                                id = "14",
                                creationDate = LocalDateTime.now(),
                                dueDate = LocalDateTime.now().plusDays(2),
                                description = "Description",
                                title = "Title",
                                image = "https://i.imgur.com/GTag1cl.png"
                            )
                        ),
                        TaskSummaryItem.from(
                            cz.kotox.common.task.poc.domain.model.Task(
                                id = "24",
                                creationDate = LocalDateTime.now(),
                                dueDate = LocalDateTime.now().plusDays(2),
                                description = "Description",
                                title = "Title",
                                image = "https://i.imgur.com/GTag1cl.png"
                            )
                        ),

                        ), titleResId = R.string.main_screen_section_title_upcoming
                )
            ),
            pagerState = PagerState(currentPage = 0)
        )
    )
}