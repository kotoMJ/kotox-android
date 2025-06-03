package cz.kotox.auth.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.kotox.common.core.android.extension.stateInForScope
import cz.kotox.feature.firebase.auth.model.FirebaseUser
import cz.kotox.feature.firebase.auth.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    // private val savedStateHandle: SavedStateHandle,
    private val accountService: AccountService
) : ViewModel() {

    private val firebaseUser: Flow<FirebaseUser> = accountService.currentUser.stateInForScope(scope = viewModelScope, initialValue = FirebaseUser.None)

    internal val state: StateFlow<ProfileViewState> = ProfileScreenPresenter(
        firebaseUserFlow = firebaseUser
    ).stateInForScope(
        scope = viewModelScope,
        initialValue = ProfileViewState(
            firebaseUser = FirebaseUser.None
        )
    )
}
