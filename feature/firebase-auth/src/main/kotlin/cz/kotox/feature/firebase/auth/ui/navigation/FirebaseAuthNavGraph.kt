package cz.kotox.feature.firebase.auth.ui.navigation

import androidx.navigation.NavGraphBuilder
import cz.kotox.feature.firebase.auth.ui.signin.dashboard.firebaseSignInDashboardScreen
import cz.kotox.feature.firebase.auth.ui.signin.email.firebaseEmailSignInScreen
import cz.kotox.feature.firebase.auth.ui.signup.dashboard.firebaseSignUpDashboardScreen
import cz.kotox.feature.firebase.auth.ui.signup.email.firebaseEmailSignUpScreen

fun NavGraphBuilder.firebaseAuthNavGraph(
    closeAuthAndPopUp: (String) -> Unit,
    onSignUpEmail: () -> Unit,
    onSignInEmail: () -> Unit,
    onSignInEmailPrefilled: (String) -> Unit
) {
    firebaseSignInDashboardScreen(
        onSignInEmail = onSignInEmail
    )
    firebaseSignUpDashboardScreen(onSignUpEmail = onSignUpEmail)
    firebaseEmailSignInScreen(
        closeAuthAndPopUp = closeAuthAndPopUp
    )
    firebaseEmailSignUpScreen(
        closeAuthAndPopUp = closeAuthAndPopUp,
        tryLoginWithEmail = onSignInEmailPrefilled
    )
}
