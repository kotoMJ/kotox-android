package cz.kotox.auth.ui.screens.login

import cz.kotox.feature.firebase.auth.model.FirebaseUser

data class LoginViewState(
    val user: FirebaseUser
)
