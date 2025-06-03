package cz.kotox.feature.firebase.auth.ui.signup.email

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

@Suppress("FunctionNaming")
internal fun FirebaseSignUpEmailScreenPresenter(
    emailFlow: Flow<String>,
    passwordFlow: Flow<String>,
    repeatPasswordFlow: Flow<String>,
    emailAlreadyInUseFlow: Flow<Boolean>
) = combine(
    emailFlow,
    passwordFlow,
    repeatPasswordFlow,
    emailAlreadyInUseFlow
) { email, password, repeatPassword, emailAlreadyInUse ->
    FirebaseSignUpEmailViewState(
        email = email,
        password = password,
        repeatPassword = repeatPassword,
        emailAlreadyInUse = emailAlreadyInUse
    )
}
