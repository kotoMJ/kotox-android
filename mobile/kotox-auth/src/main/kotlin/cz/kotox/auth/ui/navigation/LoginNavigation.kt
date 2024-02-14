package cz.kotox.auth.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import cz.kotox.auth.ui.screens.login.LoginScreen

@SuppressWarnings("TopLevelPropertyNaming")
const val LoginRoute = "login"

fun NavController.navigateToLoginScreen(
    navOptions: NavOptions? = null
) {
    navigate(
        route = LoginRoute,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.loginScreen(
    navigateToFirebaseUsernamePasswordAuthentication: () -> Unit
) {
    composable(route = LoginRoute) {
        LoginScreen(
            navigateToFirebaseUsernamePasswordAuthentication = navigateToFirebaseUsernamePasswordAuthentication
        )
    }
}
