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

fun NavGraphBuilder.firebaseEmailSignUpScreen(
    closeAuthAndPopUp: (String) -> Unit,
    tryLoginWithEmail: (String) -> Unit
) {
    composable(route = EmailSignUpRoute) {
        FirebaseSignUpEmailScreen(
            closeAuthAndPopUp = closeAuthAndPopUp,
            tryLoginWithEmail = tryLoginWithEmail
        )
    }
}
