package cz.kotox.auth.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import cz.kotox.common.designsystem.component.button.FilledTonalButton
import cz.kotox.common.designsystem.extension.basicButton
import cz.kotox.common.designsystem.preview.PreviewMobileLarge
import cz.kotox.common.designsystem.theme.hornet.HornetThemeFullSizePreview
import cz.kotox.feature.firebase.auth.model.FirebaseUser
import timber.log.Timber

@Suppress("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AuthScreen(
    navigateToFirebaseSignIn: () -> Unit,
    navigateToFirebaseSignUp: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle(initialValue = AuthViewState(FirebaseUser.None))
    AuthScreenContent(
        state = state,
        onSignInFirebaseClick = navigateToFirebaseSignIn,
        onSignUpFirebaseClick = navigateToFirebaseSignUp
    )
}

@Composable
fun AuthScreenContent(
    state: AuthViewState,
    modifier: Modifier = Modifier,
    onSignInFirebaseClick: () -> Unit,
    onSignUpFirebaseClick: () -> Unit
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

        FilledTonalButton(
            textRes = R.string.auth_screen_action_click_signIn,
            modifier = Modifier.basicButton(),
            action = onSignInFirebaseClick
        )
        FilledTonalButton(
            textRes = R.string.auth_screen_action_click_signUp,
            modifier = Modifier.basicButton(),
            action = onSignUpFirebaseClick
        )
    }
}

@PreviewMobileLarge
@Composable
private fun ProfileScanResultErrorContentPreview() {
    HornetThemeFullSizePreview {
        AuthScreenContent(
            state = AuthViewState(user = FirebaseUser.None),
            onSignInFirebaseClick = {/*Do nothing in preview*/ },
            onSignUpFirebaseClick = {/*Do nothing in preview*/ }
        )
    }
}
