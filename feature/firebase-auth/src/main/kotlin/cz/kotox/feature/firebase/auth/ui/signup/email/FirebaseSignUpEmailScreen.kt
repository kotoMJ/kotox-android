package cz.kotox.feature.firebase.auth.ui.signup.email

import androidx.compose.animation.AnimatedVisibility
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
import cz.kotox.common.designsystem.component.textfield.RepeatPasswordField
import cz.kotox.common.designsystem.extension.basicButton
import cz.kotox.common.designsystem.extension.fieldModifier
import cz.kotox.common.designsystem.preview.PreviewMobileLarge
import cz.kotox.common.designsystem.theme.hornet.HornetThemeFullSizePreview
import cz.kotox.feature.firebase.auth.R

@Composable
fun FirebaseSignUpEmailScreen(
    closeAuthAndPopUp: (String) -> Unit,
    tryLoginWithEmail: (String) -> Unit,
    viewModel: FirebaseSignUpEmailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle(initialValue = FirebaseSignUpEmailViewState())

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    FirebaseSignInEmailScreenContent(
        state = state,
        focusRequester = focusRequester,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
        onSignUpClick = {
            viewModel.onSignUpClick(
                closeAuthAndPopup = closeAuthAndPopUp
            )
        },
        tryLoginWithEmail = tryLoginWithEmail
    )
}

@Composable
fun FirebaseSignInEmailScreenContent(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    state: FirebaseSignUpEmailViewState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    tryLoginWithEmail: (String) -> Unit
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
        EmailField(
            value = state.email,
            onNewValue = onEmailChange,
            modifier = fieldModifier.focusRequester(focusRequester),
            imeAction = ImeAction.Next
        )
        PasswordField(
            value = state.password,
            onNewValue = onPasswordChange,
            modifier = fieldModifier,
            imeAction = ImeAction.Next
        )
        RepeatPasswordField(
            value = state.repeatPassword,
            onNewValue = onRepeatPasswordChange,
            modifier = fieldModifier,
            imeAction = ImeAction.Next
        )
        FilledTonalButton(
            textRes = R.string.signup_screen_sign_up,
            modifier = Modifier
                .basicButton()
                .focusable(),
            enabled = state.signUpEnabled
        ) { onSignUpClick() }

        AnimatedVisibility(visible = state.emailAlreadyInUse) {
            TextButton(onClick = { tryLoginWithEmail(state.email) }) {
                Text(text = stringResource(id = R.string.signup_screen_account_exists))
            }
        }
    }
}

@PreviewMobileLarge
@Composable
private fun FirebaseSignUpEmailScreenContentPreview() {
    HornetThemeFullSizePreview {
        FirebaseSignInEmailScreenContent(
            state = FirebaseSignUpEmailViewState(),
            focusRequester = FocusRequester(),
            onEmailChange = {/*Do nothing in preview*/ },
            onPasswordChange = {/*Do nothing in preview*/ },
            onRepeatPasswordChange = {/*Do nothing in preview*/ },
            onSignUpClick = {/*Do nothing in preview*/ },
            tryLoginWithEmail = {/*Do nothing in preview*/ }
        )
    }
}

@PreviewMobileLarge
@Composable
private fun FirebaseSignUpEmailAlreadyExistsScreenContentPreview() {
    HornetThemeFullSizePreview {
        FirebaseSignInEmailScreenContent(
            state = FirebaseSignUpEmailViewState(
                email = "email.already.registered@gmail.com",
                emailAlreadyInUse = true
            ),
            focusRequester = FocusRequester(),
            onEmailChange = {/*Do nothing in preview*/ },
            onPasswordChange = {/*Do nothing in preview*/ },
            onRepeatPasswordChange = {/*Do nothing in preview*/ },
            onSignUpClick = {/*Do nothing in preview*/ },
            tryLoginWithEmail = {/*Do nothing in preview*/ }
        )
    }
}
