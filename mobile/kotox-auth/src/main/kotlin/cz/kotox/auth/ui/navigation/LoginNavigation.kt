package cz.kotox.auth.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import cz.kotox.auth.ui.screens.auth.AuthScreen

@SuppressWarnings("TopLevelPropertyNaming")
const val AuthRoute = "auth"

fun NavController.navigateToLoginScreen(
    navOptions: NavOptions? = null
) {
    navigate(
        route = AuthRoute,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.authScreen(
    navigateToFirebaseSignIn: () -> Unit,
    navigateToFirebaseSignUp: () -> Unit
) {
    composable(route = AuthRoute) {
        AuthScreen(
            navigateToFirebaseSignIn = navigateToFirebaseSignIn,
            navigateToFirebaseSignUp = navigateToFirebaseSignUp
        )
    }
}
