package cz.kotox.task.list

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import cz.kotox.core.ui.theme.KotoxBasicTheme
import cz.kotox.task.detail.ui.TaskDetailActivity
import cz.kotox.task.detail.ui.TaskDetailScreen
import cz.kotox.task.detail.ui.TaskDetailScreenInput
import cz.kotox.task.detail.ui.UI_TEST_DOWNLOAD_BUTTON_TAG
import cz.kotox.task.detail.ui.component.TaskSummaryItem
import cz.kotox.task.domain.api.model.Task
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

class TaskDetailScreenSmokeTest {

    @get:Rule(order = 1)
    val detailTaskActivityTestRule = createAndroidComposeRule<TaskDetailActivity>()


    @Test
    fun detailScreenSmokeTest() {

        detailTaskActivityTestRule.setContent {
            KotoxBasicTheme {
                TaskDetailScreen(input = TaskDetailScreenInput(
                    TaskSummaryItem.from(
                        Task(
                            id = "14",
                            creationDate = LocalDateTime.now(),
                            dueDate = LocalDateTime.now().plusDays(2),
                            description = "Description",
                            title = "Title",
                            image = "https://i.imgur.com/GTag1cl.png"
                        )
                    ),
                ), onEventHandler = {

                })
            }
        }

        detailTaskActivityTestRule.onNodeWithTag(UI_TEST_DOWNLOAD_BUTTON_TAG).assertExists()

    }

}