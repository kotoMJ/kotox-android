package cz.kotox.feature.firebase.auth.ui.signin.email

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val ARG_EMAIL = "ARG_EMAIL"

data class NavigationCommand(
    val destination: String,
    val arguments: List<NamedNavArgument> = emptyList()
)

object FirebaseSignInDestinations {
    val email = NavigationCommand(
        destination = "emailSignIn"
    )

    val emailPrefilled = NavigationCommand(
        destination = "emailSignInPrefill",
        arguments = listOf(
            navArgument(name = ARG_EMAIL) { type = NavType.StringType }
        )
    )
}

fun NavController.navigateToFirebaseEmailSignInScreen(
    navOptions: NavOptions? = null
) {
    navigate(
        route = FirebaseSignInDestinations.email.destination,
        navOptions = navOptions
    )
}

fun NavController.navigateToFirebaseEmailSignInScreen(
    email: String,
    navOptions: NavOptions? = null
) {
    navigate(
        route = FirebaseSignInDestinations.emailPrefilled.destination + "/$email",
        navOptions = navOptions
    )
}

fun NavGraphBuilder.firebaseEmailSignInScreen() {
    composable(route = FirebaseSignInDestinations.email.destination) {
        FirebaseSignInEmailScreen()
    }

    composable(
        route = FirebaseSignInDestinations.emailPrefilled.destination +
            "/{${FirebaseSignInDestinations.emailPrefilled.arguments[0].name}}",
        arguments = FirebaseSignInDestinations.emailPrefilled.arguments
    ) {
        FirebaseSignInEmailScreen()
    }
}
