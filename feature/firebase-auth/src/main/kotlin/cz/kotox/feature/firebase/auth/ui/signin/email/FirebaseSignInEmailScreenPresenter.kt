package cz.kotox.feature.firebase.auth.ui.signin.email

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

@Suppress("FunctionNaming")
internal fun FirebaseSignInEmailScreenPresenter(
    emailFlow: Flow<String>,
    passwordFlow: Flow<String>
//    repeatPasswordFlow: Flow<String>,
//    emailAlreadyInUseFlow: Flow<Boolean>
) = combine(
    emailFlow,
    passwordFlow
//    repeatPasswordFlow,
//    emailAlreadyInUseFlow
) { email, password /*, repeatPassword, emailAlreadyInUse*/ ->
    FirebaseSignInEmailViewState(
        email = email,
        password = password
//        repeatPassword = repeatPassword,
//        emailAlreadyInUse = emailAlreadyInUse
    )
}
