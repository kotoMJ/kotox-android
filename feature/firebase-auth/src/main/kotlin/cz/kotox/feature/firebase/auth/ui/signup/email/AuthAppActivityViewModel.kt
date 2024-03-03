package cz.kotox.feature.firebase.auth.ui.signup.email

import androidx.lifecycle.ViewModel
import cz.kotox.feature.firebase.auth.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthAppActivityViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {

    val currentUser = accountService.currentUser

    fun onLogout() = accountService.logoutCurrentUser()
}