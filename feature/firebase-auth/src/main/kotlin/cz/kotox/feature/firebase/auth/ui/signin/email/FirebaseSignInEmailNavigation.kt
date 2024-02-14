package cz.kotox.feature.firebase.auth.ui.signin.email

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

@SuppressWarnings("TopLevelPropertyNaming")
const val EmailSignInRoute = "emailSignIn"

fun NavController.navigateToFirebaseEmailSignInScreen(
    navOptions: NavOptions? = null
) {
    navigate(
        route = EmailSignInRoute,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.firebaseEmailSignInScreen() {
    composable(route = EmailSignInRoute) {
        FirebaseSignInEmailScreen()
    }
}
