package cz.kotox.task.list.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import cz.kotox.common.core.error.BasicError
import cz.kotox.common.core.error.OfflineError
import cz.kotox.common.designsystem.theme.shiraz.KotoxBasicTheme
import cz.kotox.task.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.viewModelScope.launch {
            viewModel.triggerRemoteRefresh {
                runOnUiThread {
                    showErrorMessage(it)
                }
            }
        }

        setContent {
            KotoxBasicTheme {
                MainScreen(
                    input = MainScreenInput(
                        mainFeedState = viewModel.uiState.collectAsState().value,
                        isRefreshing = viewModel.remoteRefreshOngoing.collectAsState().value
                    ),
                    onEventHandler = { event ->
                        when (event) {
                            MainScreenEvent.RequestDownload -> {
                                viewModel.viewModelScope.launch {
                                    viewModel.triggerRemoteRefresh {
                                        runOnUiThread {
                                            showErrorMessage(it)
                                        }
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.viewModelScope.launch {
            viewModel.loadData()
        }
    }

    private fun showErrorMessage(error: BasicError) {
        if (error is OfflineError) {
            //We currently use toast as the simplest messaging solution.
            if (!this@MainActivity.isDestroyed && !this@MainActivity.isFinishing) {
                Toast.makeText(
                    this@MainActivity,
                    this@MainActivity.getString(R.string.offline_message),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
