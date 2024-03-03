package cz.kotox.feature.firebase.auth.ui.signin.email

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.kotox.common.core.android.extension.stateInForScope
import cz.kotox.common.core.android.flow.SaveableMutableSaveStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FirebaseSignInEmailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
//    private val accountService: AccountService
) : ViewModel() {

    // val state = accountService.currentUser.map { FirebaseSignInEmailViewState() }

    val defaultEmail: String = savedStateHandle[ARG_EMAIL] ?: ""

    private val email = SaveableMutableSaveStateFlow(
        savedStateHandle = savedStateHandle,
        key = "email",
        defaultValue = defaultEmail
    )

    private val password = SaveableMutableSaveStateFlow(
        savedStateHandle = savedStateHandle,
        key = "password",
        defaultValue = ""
    )

    internal val state: StateFlow<FirebaseSignInEmailViewState> = FirebaseSignInEmailScreenPresenter(
        emailFlow = email.state,
        passwordFlow = password.state

    ).stateInForScope(
        scope = viewModelScope,
        initialValue = FirebaseSignInEmailViewState(
            email = email.value,
            password = password.value
        )
    )
}
