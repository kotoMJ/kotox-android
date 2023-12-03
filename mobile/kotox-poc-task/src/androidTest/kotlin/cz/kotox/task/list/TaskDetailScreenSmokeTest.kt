package cz.kotox.task.list

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import cz.kotox.common.designsystem.theme.KotoxBasicTheme
import cz.kotox.feature.task.poc.detail.ui.TaskDetailActivity
import cz.kotox.feature.task.poc.detail.ui.TaskDetailScreen
import cz.kotox.feature.task.poc.detail.ui.TaskDetailScreenInput
import cz.kotox.feature.task.poc.detail.ui.UI_TEST_DOWNLOAD_BUTTON_TAG
import cz.kotox.common.task.poc.domain.model.Task
import cz.kotox.common.task.poc.ui.TaskSummaryItem
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class TaskDetailScreenSmokeTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val detailTaskActivityTestRule = createAndroidComposeRule<TaskDetailActivity>()


    @Before
    fun init() {
        hiltRule.inject()
    }


    @Ignore("TODO, Fix: Task ID NOT set! Run activity with extra ARG_TASK_ID")
    @Test
    fun detailScreenSmokeTest() {

        detailTaskActivityTestRule.setContent {
            cz.kotox.common.designsystem.theme.KotoxBasicTheme {
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