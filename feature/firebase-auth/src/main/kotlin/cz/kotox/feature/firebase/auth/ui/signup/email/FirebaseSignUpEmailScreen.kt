package cz.kotox.feature.firebase.auth.ui.signup.email

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
import cz.kotox.common.designsystem.component.textfield.RepeatPasswordField
import cz.kotox.common.designsystem.extension.basicButton
import cz.kotox.common.designsystem.extension.fieldModifier
import cz.kotox.common.designsystem.preview.PreviewMobileLarge
import cz.kotox.common.designsystem.theme.hornet.HornetThemeFullSizePreview
import cz.kotox.feature.firebase.auth.R

@Composable
fun FirebaseSignUpEmailScreen(
    closeAuthAndPopUp: (String) -> Unit,
    viewModel: FirebaseSignUpEmailViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle(initialValue = FirebaseSignUpEmailViewState())
    FirebaseSignInEmailScreenContent(
        state = state,
        onEmailChange = {},
        onForgotPasswordClick = {},
        onPasswordChange = {},
        onRepeatPasswordChange = {},
        onSignUpClick = {
            viewModel.onSignUpClick(closeAuthAndPopUp)
        }
    )
}

@Composable
fun FirebaseSignInEmailScreenContent(
    modifier: Modifier = Modifier,
    state: FirebaseSignUpEmailViewState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    val fieldModifier = Modifier.fieldModifier()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(state.email, onEmailChange, fieldModifier)
        PasswordField(state.password, onPasswordChange, fieldModifier)
        RepeatPasswordField(state.password, onRepeatPasswordChange, fieldModifier)
        FilledTonalButton(R.string.login_screen_sign_in, Modifier.basicButton()) { onSignUpClick() }

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
            state = FirebaseSignUpEmailViewState(),
            onEmailChange = {/*Do nothing in preview*/ },
            onPasswordChange = {/*Do nothing in preview*/ },
            onRepeatPasswordChange = {/*Do nothing in preview*/ },
            onSignUpClick = {/*Do nothing in preview*/ },
            onForgotPasswordClick = {/*Do nothing in preview*/ },
        )
    }
}

