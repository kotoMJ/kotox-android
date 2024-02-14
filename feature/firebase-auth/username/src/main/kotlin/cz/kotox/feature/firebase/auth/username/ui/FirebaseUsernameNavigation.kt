package cz.kotox.feature.firebase.auth.username.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

@SuppressWarnings("TopLevelPropertyNaming")
const val UsernameRoute = "usernamePassword"

fun NavController.navigateToFirebaseUsernameScreen(
    navOptions: NavOptions? = null
) {
    navigate(
        route = UsernameRoute,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.firebaseUsernameScreen() {
    composable(route = UsernameRoute) {
        FirebaseUsernameScreen()
    }
}
