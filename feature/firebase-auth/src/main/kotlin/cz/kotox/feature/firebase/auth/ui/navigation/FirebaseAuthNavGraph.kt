package cz.kotox.feature.firebase.auth.ui.navigation

import androidx.navigation.NavGraphBuilder
import cz.kotox.feature.firebase.auth.ui.signin.dashboard.firebaseSignInDashboardScreen
import cz.kotox.feature.firebase.auth.ui.signin.email.firebaseEmailSignInScreen
import cz.kotox.feature.firebase.auth.ui.signup.dashboard.firebaseSignUpDashboardScreen

fun NavGraphBuilder.firebaseAuthNavGraph(
    // close: () -> Unit,
) {
    firebaseSignInDashboardScreen()
    firebaseSignUpDashboardScreen()
    firebaseEmailSignInScreen()
}
