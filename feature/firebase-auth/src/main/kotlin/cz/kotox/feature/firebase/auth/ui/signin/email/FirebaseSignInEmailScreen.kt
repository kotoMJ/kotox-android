package cz.kotox.feature.firebase.auth.ui.signin.email

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.kotox.common.designsystem.component.button.FilledTonalButton
import cz.kotox.common.designsystem.component.textfield.EmailField
import cz.kotox.common.designsystem.component.textfield.PasswordField
import cz.kotox.common.designsystem.extension.basicButton
import cz.kotox.common.designsystem.extension.fieldModifier
import cz.kotox.common.designsystem.preview.PreviewMobileLarge
import cz.kotox.common.designsystem.theme.hornet.HornetThemeFullSizePreview
import cz.kotox.feature.firebase.auth.R

@Composable
fun FirebaseSignInEmailScreen(
    viewModel: FirebaseSignInEmailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle(initialValue = FirebaseSignInEmailViewState())
    FirebaseSignInEmailScreenContent(
        state = state,
        onEmailChange = {},
        onForgotPasswordClick = {},
        onPasswordChange = {},
        onSignInClick = {}
    )
}

@Composable
fun FirebaseSignInEmailScreenContent(
    modifier: Modifier = Modifier,
    state: FirebaseSignInEmailViewState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(state.email, onEmailChange, Modifier.fieldModifier())
        PasswordField(state.password, onPasswordChange, Modifier.fieldModifier())

        FilledTonalButton(R.string.login_screen_sign_in, Modifier.basicButton()) { onSignInClick() }

        TextButton(onClick = onForgotPasswordClick) {
            Text(text = stringResource(id = R.string.login_screen_forgot_password))
        }
    }
}

@PreviewMobileLarge
@Composable
private fun ProfileScanResultErrorContentPreview() {
    HornetThemeFullSizePreview {
        FirebaseSignInEmailScreenContent(
            state = FirebaseSignInEmailViewState(),
            onEmailChange = {/*Do nothing in preview*/ },
            onPasswordChange = {/*Do nothing in preview*/ },
            onSignInClick = {/*Do nothing in preview*/ },
            onForgotPasswordClick = {/*Do nothing in preview*/ }
        )
    }
}
