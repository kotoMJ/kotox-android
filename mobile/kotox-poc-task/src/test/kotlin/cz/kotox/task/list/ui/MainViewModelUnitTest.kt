package cz.kotox.task.list.ui

import cz.kotox.common.task.poc.domain.usecase.GetAllTasksUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class MainViewModelUnitTest {


    private lateinit var mainViewModel: MainViewModel
    private lateinit var getAllTasksUseCase: GetAllTasksUseCase
    private lateinit var getOneTaskImageUseCase: cz.kotox.common.task.poc.domain.usecase.GetOneTaskImageUseCase
    private lateinit var remoteRefreshTasksUseCase: cz.kotox.common.task.poc.domain.usecase.RefreshTasksUseCase

    @Before
    fun init() {
        getAllTasksUseCase = mockk()
        getOneTaskImageUseCase = mockk()
        remoteRefreshTasksUseCase = mockk()

        mainViewModel =
            MainViewModel(
                getAllTasksUseCase,
                getOneTaskImageUseCase,
                remoteRefreshTasksUseCase
            )
    }

    @Test
    fun initStateTest() {

        val initTaskGroups = mainViewModel.uiState.value.taskGroups
        Assert.assertEquals(
            "Task group list is expected to be initialized with two groups!",
            2,
            initTaskGroups.size,
        )

        Assert.assertTrue(
            "First group in list is expected to be initialized as empty list",
            initTaskGroups[0].tasks.isEmpty()
        )
        Assert.assertTrue(
            "Second group in list is expected to be initialized as empty list",
            initTaskGroups[1].tasks.isEmpty()
        )
    }

    @Test
    fun loadStateTest() = runBlockingTest {

        coEvery { getOneTaskImageUseCase.execute(any()) } returns "mocked_image_path"

        coEvery { getAllTasksUseCase.execute() } returns flow {
            emit(
                listOf(
                    cz.kotox.common.task.poc.domain.model.Task(
                        creationDate = LocalDateTime.now().minusDays(10),
                        dueDate = LocalDateTime.now().minusDays(9),
                        description = "desc1",
                        title = "title1",
                        id = "1",
                        image = "fake_image_path_1"
                    ),
                    cz.kotox.common.task.poc.domain.model.Task(
                        creationDate = LocalDateTime.now().minusDays(8),
                        dueDate = LocalDateTime.now().plusDays(1),
                        description = "desc1",
                        title = "title1",
                        id = "2",
                        image = "fake_image_path_1"
                    )
                )
            )
        }

        loadData(mainViewModel)

        coVerify { getAllTasksUseCase.execute() }

        val initTaskGroups = mainViewModel.uiState.value.taskGroups
        Assert.assertEquals(
            "Task group list is expected to be initialized with two groups!",
            2,
            initTaskGroups.size,
        )

        Assert.assertTrue(
            "First group in list is expected to be initialized with one item",
            initTaskGroups[0].tasks.size == 2 //All tasks in first group
        )
        Assert.assertTrue(
            "First element of ALL TASKS group is expected to be 1",
            initTaskGroups[0].tasks[0].id == "1"
        )
        Assert.assertTrue(
            "Second element of ALL TASKS group is expected to be 2",
            initTaskGroups[0].tasks[1].id == "2"
        )

        Assert.assertTrue(
            "Second group in list is expected to be initialized with one item",
            initTaskGroups[1].tasks.size == 1 //One future task in the second group
        )

        Assert.assertTrue(
            "First element of FUTURE group is expected to be 2",
            initTaskGroups[1].tasks[0].id == "2"
        )

    }

    private fun CoroutineScope.loadData(mainViewModel: MainViewModel): Job = launch {
        mainViewModel.loadData()
    }


}