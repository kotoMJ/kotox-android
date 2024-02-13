package cz.kotox.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import cz.kotox.auth.ui.screens.settings.SettingScreen

const val SettingRoute = "setting"

fun NavController.navigateToSettingScreen(
    navOptions: NavOptions? = null,
) {
    navigate(
        route = SettingRoute,
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.settingScreen() {
    composable(route = SettingRoute) {
        SettingScreen()
    }
}