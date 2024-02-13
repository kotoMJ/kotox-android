package cz.kotox.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import cz.kotox.auth.ui.screens.login.LoginScreen

const val LoginRoute = "login"

fun NavController.navigateToLoginScreen(
    navOptions: NavOptions? = null,
) {
    navigate(
        route = LoginRoute,
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.loginScreen() {
    composable(route = LoginRoute) {
        LoginScreen()
    }
}