package cz.kotox.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import cz.kotox.auth.ui.screens.dashboard.DashboardScreen
import timber.log.Timber

fun NavGraphBuilder.authAppGraph(appState: AuthAppState) {
    composable(DASHBOARD_SCREEN) {
        Timber.d("AppState: $appState")
        DashboardScreen()
    }
}
