package cz.kotox.feature.firebase.auth.ui.signup.email

data class FirebaseSignUpEmailViewState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val emailAlreadyInUse: Boolean = false
){
    val signUpEnabled: Boolean = !emailAlreadyInUse
}
