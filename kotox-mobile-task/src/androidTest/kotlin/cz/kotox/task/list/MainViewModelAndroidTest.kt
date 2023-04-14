package cz.kotox.task.list

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import cz.kotox.task.list.ui.MainViewModel
import dagger.hilt.android.testing.BindValue
import io.mockk.mockk

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainViewModelAndroidTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)


    @BindValue
    @JvmField
    val mainViewModel = MainViewModel(
        getAllTasksUseCase = mockk(),
        getOneTaskImageUseCase = mockk(),
        remoteRefreshTasksUseCase = mockk()
    )//mockk<MainViewModel>(relaxed = true)


    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun basic() {
        Assert.assertTrue(
            "Expected size is 2, current is ${mainViewModel.uiState.value.taskGroups.size}",
            mainViewModel.uiState.value.taskGroups.size == 2
        )
    }

}