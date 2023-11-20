package cz.kotox.task.list

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import cz.kotox.common.ui.theme.KotoxBasicTheme
import cz.kotox.common.task.poc.domain.mapper.toTask
import cz.kotox.common.task.poc.data.impl.remote.dto.TaskDTO
import cz.kotox.task.list.ui.MainFeedState
import cz.kotox.task.list.ui.MainScreen
import cz.kotox.task.list.ui.MainScreenInput
import cz.kotox.task.list.ui.TaskGroup
import cz.kotox.task.R
import cz.kotox.common.task.poc.ui.TaskSummaryItem
import cz.kotox.task.list.ui.UI_TEST_MAIN_LIST_TAG
import org.junit.Rule
import org.junit.Test


class TaskMainScreenSmokeTest {


    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun mainScreenListDisplayedSmokeTest() {

        composeTestRule.setContent {
            KotoxBasicTheme {
                MainScreen(
                    input = MainScreenInput(
                        mainFeedState = MainFeedState(
                            isInitialLoading = false,
                            taskGroups = listOf(
                                TaskGroup(
                                    tasks = listOf(
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
                                    ), titleResId = R.string.main_screen_section_title_all
                                ),
                                TaskGroup(
                                    tasks = listOf(
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
                                    ), titleResId = R.string.main_screen_section_title_upcoming
                                )
                            )
                        )
                    )
                )
            }
        }

        //Expect lazy column to be available
        composeTestRule.onNodeWithTag(UI_TEST_MAIN_LIST_TAG + 0).assertExists()

    }

    @Test
    fun mainScreenListEmptySmokeTest() {

        composeTestRule.setContent {
            KotoxBasicTheme {
                MainScreen(
                    input = MainScreenInput(
                        mainFeedState = MainFeedState(
                            isInitialLoading = false,
                            taskGroups = listOf(
                                TaskGroup(
                                    tasks = listOf(),
                                    titleResId = R.string.main_screen_section_title_all
                                ),
                                TaskGroup(
                                    tasks = listOf(
                                    ), titleResId = R.string.main_screen_section_title_upcoming
                                )
                            )
                        )
                    )
                )
            }
        }

        //Expect lazy column to be missing
        composeTestRule.onNodeWithTag(UI_TEST_MAIN_LIST_TAG + 0).assertDoesNotExist()

    }

}