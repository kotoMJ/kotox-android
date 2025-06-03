package cz.kotox.feature.firebase.auth.ui.signin.email

import cz.kotox.feature.firebase.auth.model.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

@Suppress("FunctionNaming")
internal fun FirebaseSignInEmailScreenPresenter(
    emailFlow: Flow<String>,
    passwordFlow: Flow<String>,
    firebaseUserFlow: Flow<FirebaseUser>
) = combine(
    emailFlow,
    passwordFlow,
    firebaseUserFlow
) { email, password, firebaseUser ->
    FirebaseSignInEmailViewState(
        email = email,
        password = password,
        firebaseUser = firebaseUser
    )
}
