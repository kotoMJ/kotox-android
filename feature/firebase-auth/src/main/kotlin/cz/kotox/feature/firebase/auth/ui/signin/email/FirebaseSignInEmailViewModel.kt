package cz.kotox.feature.firebase.auth.ui.signin.email

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.kotox.common.core.android.extension.launchCatching
import cz.kotox.common.core.android.extension.stateInForScope
import cz.kotox.common.core.android.flow.SaveableMutableSaveStateFlow
import cz.kotox.common.core.android.snackbar.SnackbarMessageHandler
import cz.kotox.feature.firebase.auth.R
import cz.kotox.feature.firebase.auth.model.FirebaseUser
import cz.kotox.feature.firebase.auth.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FirebaseSignInEmailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val accountService: AccountService
) : ViewModel() {

    private val firebaseUser: Flow<FirebaseUser> = accountService.currentUser.stateInForScope(scope = viewModelScope, initialValue = FirebaseUser.None)

    private val defaultEmail: String? = savedStateHandle[ARG_EMAIL]

    private val email = SaveableMutableSaveStateFlow(
        savedStateHandle = savedStateHandle,
        key = "email",
        defaultValue = defaultEmail ?: ""
    )

    private val password = SaveableMutableSaveStateFlow(
        savedStateHandle = savedStateHandle,
        key = "password",
        defaultValue = ""
    )

    internal val state: StateFlow<FirebaseSignInEmailViewState> = FirebaseSignInEmailScreenPresenter(
        emailFlow = email.state,
        passwordFlow = password.state,
        firebaseUserFlow = firebaseUser
    ).stateInForScope(
        scope = viewModelScope,
        initialValue = FirebaseSignInEmailViewState(
            email = email.value,
            password = password.value,
            firebaseUser = FirebaseUser.None
        )
    )

    fun onEmailChange(newValue: String) {
        email.value = newValue
    }

    fun onPasswordChange(newValue: String) {
        password.value = newValue
    }

    fun onSignInClick(
        closeAuthAndPopup: (String) -> Unit
    ) {
        this.launchCatching() {
            if (accountService.loginUserEmail(
                    email = state.value.email,
                    password = state.value.password,
                    onInvalidCredentials = {
                        SnackbarMessageHandler.showMessage(R.string.login_email_screen_invalid_password)
                    }
                )
            ) {
                closeAuthAndPopup(
                    if (defaultEmail == null) {
                        FirebaseSignInDestinations.email.destination
                    } else {
                        FirebaseSignInDestinations.emailPrefilled.destination
                    }
                )
            }
        }
    }
}
