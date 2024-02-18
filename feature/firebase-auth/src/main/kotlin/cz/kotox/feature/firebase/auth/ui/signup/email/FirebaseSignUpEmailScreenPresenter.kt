package cz.kotox.feature.firebase.auth.ui.signup.email

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

@Suppress("FunctionNaming")
internal fun FirebaseSignUpEmailScreenPresenter(
    emailFlow: Flow<String>,
    passwordFlow: Flow<String>,
    repeatPasswordFlow: Flow<String>
) = combine(
    emailFlow,
    passwordFlow,
    repeatPasswordFlow
) { email, password, repeatPassword ->
    FirebaseSignUpEmailViewState(
        email = email,
        password = password,
        repeatPassword = repeatPassword
    )
}