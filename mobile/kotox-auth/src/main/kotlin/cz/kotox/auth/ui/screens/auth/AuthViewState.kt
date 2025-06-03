package cz.kotox.auth.ui.screens.auth

import cz.kotox.feature.firebase.auth.model.FirebaseUser

data class AuthViewState(
    val user: FirebaseUser
)
