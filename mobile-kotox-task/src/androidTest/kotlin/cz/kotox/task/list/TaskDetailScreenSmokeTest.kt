package cz.kotox.task.list

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import cz.kotox.core.ui.theme.KotoxBasicTheme
import cz.kotox.domain.task.api.factory.toTask
import cz.kotox.domain.task.impl.remote.dto.TaskDTO
import cz.kotox.task.detail.ui.TaskDetailActivity
import cz.kotox.task.detail.ui.TaskDetailScreen
import cz.kotox.task.detail.ui.TaskDetailScreenInput
import cz.kotox.task.detail.ui.UI_TEST_DOWNLOAD_BUTTON_TAG
import cz.kotox.task.detail.ui.component.TaskSummaryItem
import org.junit.Rule
import org.junit.Test

class TaskDetailScreenSmokeTest {

    @get:Rule(order = 1)
    val detailTaskActivityTestRule = createAndroidComposeRule<TaskDetailActivity>()


    @Test
    fun detailScreenSmokeTest() {

        detailTaskActivityTestRule.setContent {
            KotoxBasicTheme {
                TaskDetailScreen(input = TaskDetailScreenInput(
                    TaskSummaryItem.from(
                        TaskDTO(
                            creationDate = "2016-04-23T18:25:43.511Z",
                            dueDate = "2017-01-23T18:00:00.511Z",
                            encryptedDescription = "VGhlIGRpZmZlcmVuY2UgaW4gYmV0d2VlbiB0aGUgcHJvZ3Jlc3MgYW5kIHRoZSByZXN1bHQuCgo=",
                            encryptedTitle = "SW4gcHJvZ3Jlc3MgYW5kIHRoZSByZXN1bHQKCg==",
                            id = "1",
                            encryptedImage = "aHR0cHM6Ly9pLnBpY3N1bS5waG90b3MvaWQvMTMzLzI3NDIvMTgyOC5qcGc/aG1hYz0wWDVvOGJIVUlDa09JdlpIdHlrQ1JMNTBCam4xTjh3MUF2a2VuRjduOTNF"
                        ).toTask()
                    ),
                ), onEventHandler = {

                })
            }
        }

        detailTaskActivityTestRule.onNodeWithTag(UI_TEST_DOWNLOAD_BUTTON_TAG).assertExists()

    }

}