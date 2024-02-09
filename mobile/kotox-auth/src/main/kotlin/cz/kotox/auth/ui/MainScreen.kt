package cz.kotox.auth.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cz.kotox.auth.R
import cz.kotox.common.designsystem.preview.PreviewMobileLarge
import cz.kotox.common.designsystem.theme.orange.KotoxOrangeTheme
import cz.kotox.common.designsystem.theme.orange.KotoxOrangeThemeFullSizePreview
import cz.kotox.common.ui.compose.button.BasicButton
import cz.kotox.common.ui.compose.extension.basicButton

// sealed class MainScreenEvent {
//    data object ClickMe : MainScreenEvent()
// }

@Suppress("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    // onEventHandler: (MainScreenEvent) -> Unit = {}
) {
    KotoxOrangeTheme {
        Scaffold(
            backgroundColor = MaterialTheme.colors.surface,
            modifier = Modifier.systemBarsPadding(),
            topBar = {
                Column {
                    MainScreenAppBarView(
//                        onEventHandler = { event ->
//                        },
                        input = MainScreenAppBarInput(stringResource(id = R.string.main_screen_title))
                    )
                }
            },
            content = { _ ->

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BasicButton(
                        textRes = R.string.main_screen_action_click_username_password,
                        modifier = Modifier.basicButton()
                    ) { }
                    BasicButton(
                        textRes = R.string.main_screen_action_click_anonymous,
                        modifier = Modifier.basicButton()
                    ) { }
                }
            }
        )
    }
}

@PreviewMobileLarge
@Composable
private fun ProfileScanResultErrorContentPreview() {
    KotoxOrangeThemeFullSizePreview {
        MainScreen(
            // onEventHandler = {}
        )
    }
}
