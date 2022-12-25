package cz.kotox.task.list

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import cz.kotox.task.list.ui.MainViewModel2
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import androidx.activity.viewModels
import cz.kotox.task.list.ui.MainViewModel

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainViewModelTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)


//    @Inject
//    lateinit var mainViewModel: MainViewModel2

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