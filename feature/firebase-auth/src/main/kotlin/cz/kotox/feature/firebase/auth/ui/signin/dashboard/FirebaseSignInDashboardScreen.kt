package cz.kotox.feature.firebase.auth.ui.signin.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cz.kotox.common.designsystem.component.button.FilledTonalButton
import cz.kotox.common.designsystem.extension.basicButton
import cz.kotox.common.designsystem.preview.PreviewMobileLarge
import cz.kotox.common.designsystem.theme.hornet.HornetThemeFullSizePreview
import cz.kotox.feature.firebase.auth.R

@Composable
fun FirebaseSignInDashboardScreen(
    onSignInEmail: () -> Unit,
    modifier: Modifier = Modifier
    // viewModel: FirebaseSignInDashboardViewModel = hiltViewModel()
) {
    FirebaseSignInDashboardScreenContent(
        modifier = modifier,
        onSignInEmail = onSignInEmail
    )
}

@Composable
fun FirebaseSignInDashboardScreenContent(
    onSignInEmail: () -> Unit,
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
        FilledTonalButton(
            textRes = R.string.login_dashboard_screen_sign_in_email,
            modifier = Modifier.basicButton(),
            action = onSignInEmail
        )
    }
}

@PreviewMobileLarge
@Composable
private fun ProfileScanResultErrorContentPreview() {
    HornetThemeFullSizePreview {
        FirebaseSignInDashboardScreen({})
    }
}

