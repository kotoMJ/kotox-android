package cz.kotox.feature.firebase.auth.ui.signin.email

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
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
    closeAuthAndPopUp: (String) -> Unit,
    viewModel: FirebaseSignInEmailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle(initialValue = FirebaseSignInEmailViewState())

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    FirebaseSignInEmailScreenContent(
        state = state,
        focusRequester = focusRequester,
        onEmailChange = viewModel::onEmailChange,
        onForgotPasswordClick = {},
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = {
            viewModel.onSignInClick(
                closeAuthAndPopup = closeAuthAndPopUp
            )
        }
    )
}

@Composable
fun FirebaseSignInEmailScreenContent(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
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
        EmailField(
            state.email,
            onEmailChange,
            Modifier
                .fieldModifier()
                .focusRequester(focusRequester),
            imeAction = ImeAction.Next
        )
        PasswordField(
            value = state.password,
            onNewValue = onPasswordChange,
            modifier = Modifier.fieldModifier(),
            imeAction = ImeAction.Next
        )

        FilledTonalButton(
            R.string.login_email_screen_sign_in,
            Modifier
                .basicButton()
                .focusable()
        ) { onSignInClick() }

        TextButton(onClick = onForgotPasswordClick) {
            Text(text = stringResource(id = R.string.login_email_screen_forgot_password))
        }
    }
}

@PreviewMobileLarge
@Composable
private fun ProfileScanResultErrorContentPreview() {
    HornetThemeFullSizePreview {
        FirebaseSignInEmailScreenContent(
            state = FirebaseSignInEmailViewState(),
            focusRequester = FocusRequester(),
            onEmailChange = {/*Do nothing in preview*/ },
            onPasswordChange = {/*Do nothing in preview*/ },
            onSignInClick = {/*Do nothing in preview*/ },
            onForgotPasswordClick = {/*Do nothing in preview*/ }
        )
    }
}
