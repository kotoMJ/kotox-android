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
import cz.kotox.auth.domain.model.User
import cz.kotox.common.designsystem.component.button.FilledTonalButton
import cz.kotox.common.designsystem.extension.basicButton
import cz.kotox.common.designsystem.extension.spacer
import cz.kotox.common.designsystem.preview.PreviewMobileLarge
import cz.kotox.common.designsystem.theme.hornet.HornetThemeFullSizePreview
import timber.log.Timber

@Suppress("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle(initialValue = DashboardViewState(User.None))
    DashboardScreenContent(state)
}

@Composable
fun DashboardScreenContent(
    state: DashboardViewState,
    modifier: Modifier = Modifier
) {
    Timber.d("state.user = ${state.user}")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.spacer())

        FilledTonalButton(
            textRes = R.string.main_screen_action_click_username_password,
            modifier = Modifier.basicButton()
        ) { }
        FilledTonalButton(
            textRes = R.string.main_screen_action_click_anonymous,
            modifier = Modifier.basicButton()
        ) { }
    }
}

@PreviewMobileLarge
@Composable
private fun ProfileScanResultErrorContentPreview() {
    HornetThemeFullSizePreview {
        DashboardScreenContent(
            state = DashboardViewState(user = User.None)
            // onEventHandler = {}
        )
    }
}
