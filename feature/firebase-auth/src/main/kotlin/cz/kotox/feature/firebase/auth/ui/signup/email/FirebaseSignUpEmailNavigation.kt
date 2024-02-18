package cz.kotox.feature.firebase.auth.ui.signup.email

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

@SuppressWarnings("TopLevelPropertyNaming")
const val EmailSignUpRoute = "emailSignUp"

fun NavController.navigateToFirebaseEmailSignUpScreen(
    navOptions: NavOptions? = null
) {
    navigate(
        route = EmailSignUpRoute,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.firebaseEmailSignInScreen() {
    composable(route = EmailSignUpRoute) {
        FirebaseSignUpEmailScreen()
    }
}
