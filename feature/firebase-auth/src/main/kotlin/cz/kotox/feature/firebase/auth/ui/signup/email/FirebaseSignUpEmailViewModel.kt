package cz.kotox.feature.firebase.auth.ui.signup.email

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.kotox.common.core.android.extension.isValidEmail
import cz.kotox.common.core.android.extension.isValidPassword
import cz.kotox.common.core.android.extension.launchCatching
import cz.kotox.common.core.android.extension.passwordMatches
import cz.kotox.common.core.android.extension.stateInForScope
import cz.kotox.common.core.android.flow.SaveableMutableSaveStateFlow
import cz.kotox.common.core.android.snackbar.SnackbarMessageHandler
import cz.kotox.common.core.logging.LogService
import cz.kotox.feature.firebase.auth.R
import cz.kotox.feature.firebase.auth.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FirebaseSignUpEmailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val accountService: AccountService,
    //private val logService: LogService
) : ViewModel() {

    //val state = accountService.currentUser.map { FirebaseSignUpEmailViewState() }

    private val email = SaveableMutableSaveStateFlow(
        savedStateHandle = savedStateHandle,
        key = "email",
        defaultValue = ""
    )

    private val password = SaveableMutableSaveStateFlow(
        savedStateHandle = savedStateHandle,
        key = "password",
        defaultValue = ""
    )

    private val repeatPassword = SaveableMutableSaveStateFlow(
        savedStateHandle = savedStateHandle,
        key = "repeatPassword",
        defaultValue = ""
    )

    internal val state: StateFlow<FirebaseSignUpEmailViewState> = FirebaseSignUpEmailScreenPresenter(
        emailFlow = email.state,
        passwordFlow = password.state,
        repeatPasswordFlow = repeatPassword.state
    ).stateInForScope(
        scope = viewModelScope,
        initialValue = FirebaseSignUpEmailViewState(
            email = email.value,
            password = password.value,
            repeatPassword = repeatPassword.value
        )
    )

    fun onSignUpClick(closeAuthAndPopup: (String) -> Unit) {
        if (!email.value.isValidEmail()) {
            SnackbarMessageHandler.showMessage(R.string.signup_email_error)
            return
        }

        if (!password.value.isValidPassword()) {
            SnackbarMessageHandler.showMessage(R.string.signup_password_error)
            return
        }

        if (!password.value.passwordMatches(state.value.repeatPassword)) {
            SnackbarMessageHandler.showMessage(R.string.signup_password_match_error)
            return
        }

        this.launchCatching() {
            accountService.createAccount(email = email.value, password = password.value)
            closeAuthAndPopup(EmailSignUpRoute)
        }
    }
}
