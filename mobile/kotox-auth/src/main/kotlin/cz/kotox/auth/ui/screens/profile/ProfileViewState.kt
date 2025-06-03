package cz.kotox.auth.ui.screens.profile

import cz.kotox.feature.firebase.auth.model.FirebaseUser

data class ProfileViewState(
    val firebaseUser: FirebaseUser = FirebaseUser.None
)
