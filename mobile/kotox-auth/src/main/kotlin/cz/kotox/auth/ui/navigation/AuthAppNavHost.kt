package cz.kotox.auth.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import cz.kotox.feature.firebase.auth.ui.firebaseUsernameScreen
import cz.kotox.feature.firebase.auth.ui.navigateToFirebaseUsernameScreen

@Composable
internal fun AuthAppNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = LoginRoute,
        modifier = modifier
    ) {
        loginScreen(
            navigateToFirebaseUsernamePasswordAuthentication = {
                navController.navigateToFirebaseUsernameScreen()
            }
        )
        profileScreen()
        settingScreen()
        firebaseUsernameScreen()
    }
}
