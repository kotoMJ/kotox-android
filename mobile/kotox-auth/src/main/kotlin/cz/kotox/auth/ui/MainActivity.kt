package cz.kotox.auth.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cz.kotox.common.designsystem.theme.orange.KotoxOrangeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KotoxOrangeTheme {
                MainScreen(
//                    onEventHandler = { event ->
//                        when (event) {
//                            MainScreenEvent.ClickMe -> {
//                            }
//
//                            else -> {
//                            }
//                        }
//                    }
                )
            }
        }
    }
}
