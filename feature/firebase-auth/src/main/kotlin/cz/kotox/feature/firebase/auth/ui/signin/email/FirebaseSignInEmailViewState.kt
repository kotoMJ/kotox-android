package cz.kotox.feature.firebase.auth.ui.signin.email

import cz.kotox.feature.firebase.auth.model.FirebaseUser

data class FirebaseSignInEmailViewState(
    val email: String = "",
    val password: String = "",
    val firebaseUser: FirebaseUser = FirebaseUser.None
)
