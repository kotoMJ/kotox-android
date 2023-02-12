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

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainViewModelAndroidTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var mainViewModel: MainViewModel


    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun basic() {

        Assert.assertTrue(mainViewModel.uiState.value.taskGroups.isEmpty())
    }

}