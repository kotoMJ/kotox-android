package cz.kotox.feature.firebase.auth.ui.signup.dashboard

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

@SuppressWarnings("TopLevelPropertyNaming")
const val SignUpDashboardRoute = "signUpDashboard"

fun NavController.navigateToFirebaseSignUpDashboardScreen(
    navOptions: NavOptions? = null
) {
    navigate(
        route = SignUpDashboardRoute,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.firebaseSignUpDashboardScreen(
    onSignUpEmail: () -> Unit
) {
    composable(
        route = SignUpDashboardRoute
    ) {
        FirebaseSignUpDashboard(
            onSignUpEmail = onSignUpEmail
        )
    }
}
