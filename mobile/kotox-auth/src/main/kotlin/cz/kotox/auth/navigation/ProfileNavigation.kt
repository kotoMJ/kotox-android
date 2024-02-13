package cz.kotox.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import cz.kotox.auth.ui.screens.login.LoginScreen
import cz.kotox.auth.ui.screens.profile.ProfileScreen

const val ProfileRoute = "profile"

fun NavController.navigateToProfileScreen(
    navOptions: NavOptions? = null,
) {
    navigate(
        route = ProfileRoute,
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.profileScreen() {
    composable(route = ProfileRoute) {
        ProfileScreen()
    }
}