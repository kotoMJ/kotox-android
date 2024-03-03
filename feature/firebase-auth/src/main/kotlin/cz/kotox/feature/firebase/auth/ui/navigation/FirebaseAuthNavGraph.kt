package cz.kotox.feature.firebase.auth.ui.navigation

import androidx.navigation.NavGraphBuilder
import cz.kotox.feature.firebase.auth.ui.signin.dashboard.firebaseSignInDashboardScreen
import cz.kotox.feature.firebase.auth.ui.signin.email.firebaseEmailSignInScreen
import cz.kotox.feature.firebase.auth.ui.signup.dashboard.firebaseSignUpDashboardScreen
import cz.kotox.feature.firebase.auth.ui.signup.email.firebaseEmailSignUpScreen

fun NavGraphBuilder.firebaseAuthNavGraph(
    closeAuthAndPopUp: (String) -> Unit,
    onSignUpEmail: () -> Unit,
    onSignInEmail: (String) -> Unit
) {
    firebaseSignInDashboardScreen()
    firebaseSignUpDashboardScreen(onSignUpEmail = onSignUpEmail)
    firebaseEmailSignInScreen()
    firebaseEmailSignUpScreen(
        closeAuthAndPopUp = closeAuthAndPopUp,
        tryLoginWithEmail = onSignInEmail
    )
}
