package cz.kotox.feature.firebase.auth.model

sealed class FirebaseUser {
    data object None : FirebaseUser()
    data class Anonymous(val id: String) : FirebaseUser()
    data class Authorized(val id: String) : FirebaseUser()
}
