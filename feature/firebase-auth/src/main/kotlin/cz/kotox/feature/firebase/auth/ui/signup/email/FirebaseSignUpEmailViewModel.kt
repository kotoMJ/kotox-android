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
import cz.kotox.feature.firebase.auth.R
import cz.kotox.feature.firebase.auth.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FirebaseSignUpEmailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val accountService: AccountService
    // private val logService: LogService
) : ViewModel() {

    // val state = accountService.currentUser.map { FirebaseSignUpEmailViewState() }

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

    private val emailAlreadyInUse = SaveableMutableSaveStateFlow(
        savedStateHandle = savedStateHandle,
        key = "emailAlreadyInUse",
        defaultValue = false
    )

    internal val state: StateFlow<FirebaseSignUpEmailViewState> = FirebaseSignUpEmailScreenPresenter(
        emailFlow = email.state,
        passwordFlow = password.state,
        repeatPasswordFlow = repeatPassword.state,
        emailAlreadyInUseFlow = emailAlreadyInUse.state
    ).stateInForScope(
        scope = viewModelScope,
        initialValue = FirebaseSignUpEmailViewState(
            email = email.value,
            password = password.value,
            repeatPassword = repeatPassword.value,
            emailAlreadyInUse = emailAlreadyInUse.value
        )
    )

    fun onEmailChange(newValue: String) {
        emailAlreadyInUse.value = false
        email.value = newValue
    }

    fun onPasswordChange(newValue: String) {
        password.value = newValue
    }

    fun onRepeatPasswordChange(newValue: String) {
        repeatPassword.value = newValue
    }

    fun onSignUpClick(
        closeAuthAndPopup: (String) -> Unit
    ) {
        when {
            !email.value.isValidEmail() -> {
                SnackbarMessageHandler.showMessage(R.string.signup_email_error)
            }

            !password.value.isValidPassword() -> {
                SnackbarMessageHandler.showMessage(R.string.signup_password_error)
            }

            !password.value.passwordMatches(state.value.repeatPassword) -> {
                SnackbarMessageHandler.showMessage(R.string.signup_password_match_error)
            }

            else -> {
                this.launchCatching() {
                    if (accountService.createAccount(
                            email = email.value,
                            password = password.value,
                            emailAlreadyInUse = { _ ->
                                emailAlreadyInUse.value = true
                                SnackbarMessageHandler.showMessage(R.string.signup_screen_already_in_use)
                            }
                        )
                    ) {
                        closeAuthAndPopup(EmailSignUpRoute)
                    }
                }
            }
        }
    }
}
