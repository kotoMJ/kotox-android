package cz.kotox.auth.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.kotox.auth.R
import cz.kotox.common.designsystem.preview.PreviewMobileLarge
import cz.kotox.common.designsystem.theme.orange.KotoxOrangeThemeFullSizePreview
import cz.kotox.common.ui.compose.button.BasicButton
import cz.kotox.common.ui.compose.extension.basicButton
import cz.kotox.common.ui.compose.extension.spacer
import cz.kotox.common.ui.compose.toolbar.BasicToolbar

@Suppress("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle(initialValue = DashboardViewState(null))
    DashboardScreenContent(state)
}

@Composable
fun DashboardScreenContent(
    state: DashboardViewState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicToolbar("user: ${state.user?.id ?: "not logged in"}")

        Spacer(modifier = Modifier.spacer())

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

@PreviewMobileLarge
@Composable
private fun ProfileScanResultErrorContentPreview() {
    KotoxOrangeThemeFullSizePreview {
        DashboardScreenContent(
            state = DashboardViewState(user = null)
            // onEventHandler = {}
        )
    }
}
