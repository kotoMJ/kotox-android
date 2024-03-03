package cz.kotox.auth.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import cz.kotox.feature.firebase.auth.ui.navigation.firebaseAuthNavGraph
import cz.kotox.feature.firebase.auth.ui.signin.dashboard.navigateToFirebaseSignInDashboardScreen
import cz.kotox.feature.firebase.auth.ui.signin.email.navigateToFirebaseEmailSignInScreen
import cz.kotox.feature.firebase.auth.ui.signup.dashboard.navigateToFirebaseSignUpDashboardScreen
import cz.kotox.feature.firebase.auth.ui.signup.email.navigateToFirebaseEmailSignUpScreen
import timber.log.Timber

@Composable
internal fun AuthAppNavHost(
    navController: NavHostController,
    modifier: Modifier,
    closeAuthAndPopUp: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = AuthRoute,
        modifier = modifier
    ) {
        authScreen(
            navigateToFirebaseSignIn = {
                navController.navigateToFirebaseSignInDashboardScreen()
            },
            navigateToFirebaseSignUp = {
                navController.navigateToFirebaseSignUpDashboardScreen()
            }
        )
        profileScreen()
        settingScreen()

        firebaseAuthNavGraph(
            closeAuthAndPopUp = closeAuthAndPopUp,
            onSignUpEmail = {
                Timber.d(">>>_ let's navigate to SignUpEmail")
                navController.navigateToFirebaseEmailSignUpScreen(null)
            },
            onSignInEmail = { email ->
                Timber.d(">>>_ let's navigate to SignInEmail with: $email")
                navController.navigateToFirebaseEmailSignInScreen(email)
            }
        )
    }
}
