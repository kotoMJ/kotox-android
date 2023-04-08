package cz.kotox.starter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import cz.kotox.core.ui.theme.KotoxBasicTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            KotoxBasicTheme {
                MainScreen(
                    onEventHandler = { event ->
                        when (event) {
                            MainScreenEvent.ClickMe -> {

                            }

                            else -> {

                            }
                        }
                    }
                )
            }
        }
    }
}
