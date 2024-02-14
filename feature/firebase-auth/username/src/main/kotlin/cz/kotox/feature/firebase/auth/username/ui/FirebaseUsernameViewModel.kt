package cz.kotox.feature.firebase.auth.username.ui

import androidx.lifecycle.ViewModel
import cz.kotox.auth.domain.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FirebaseUsernameViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {

    val state = accountService.currentUser.map { FirebaseUsernameViewState(it) }
}
