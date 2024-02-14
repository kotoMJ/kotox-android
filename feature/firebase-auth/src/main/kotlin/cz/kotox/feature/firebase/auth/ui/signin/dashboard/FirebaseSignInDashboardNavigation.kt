package cz.kotox.feature.firebase.auth.ui.signin.dashboard

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

@SuppressWarnings("TopLevelPropertyNaming")
const val SignInDashboardRoute = "signInDashboard"

fun NavController.navigateToFirebaseSignInDashboardScreen(
    navOptions: NavOptions? = null
) {
    navigate(
        route = SignInDashboardRoute,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.firebaseSignInDashboardScreen() {
    composable(route = SignInDashboardRoute) {
        FirebaseSignInDashboard()
    }
}
