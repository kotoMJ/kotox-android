package cz.kotox.auth.ui

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cz.kotox.auth.ui.navigation.AuthAppNavHost
import cz.kotox.auth.ui.navigation.AuthAppState
import cz.kotox.auth.ui.navigation.AuthBottomNavigation
import cz.kotox.auth.ui.navigation.ProfileRoute
import cz.kotox.common.core.android.snackbar.SnackbarMessageHandler
import cz.kotox.common.designsystem.component.snackbar.KotoxSnackbar
import cz.kotox.common.designsystem.extension.enableEdgeToEdge
import cz.kotox.common.designsystem.extension.isDarkMode
import cz.kotox.common.designsystem.theme.hornet.HornetAppTheme
import cz.kotox.feature.firebase.auth.model.FirebaseUser
import cz.kotox.feature.firebase.auth.ui.signup.email.AuthAppActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import androidx.compose.material3.MaterialTheme as Material3Theme
import androidx.compose.material3.Scaffold as Material3Scaffold
import androidx.compose.material3.SnackbarHost as Material3SnackbarHost
import androidx.compose.material3.SnackbarHostState as Material3SnackbarHostState
import androidx.compose.material3.Surface as Material3Surface

@AndroidEntryPoint
class AuthAppActivity : ComponentActivity() {

    private lateinit var snackBarHostState: Material3SnackbarHostState
    private var navHostListener: NavController.OnDestinationChangedListener =
        NavController.OnDestinationChangedListener { _, _, _ -> snackBarHostState.currentSnackbarData?.dismiss() }

    private val viewModel: AuthAppActivityViewModel by viewModels<AuthAppActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(isDarkMode())
        WindowCompat.setDecorFitsSystemWindows(window, true)
        super.onCreate(savedInstanceState)

        setContent {
            snackBarHostState = remember { Material3SnackbarHostState() }

            val state by viewModel.currentUser.collectAsStateWithLifecycle(initialValue = FirebaseUser.None)

            HornetAppTheme {
                Material3Surface(
                    color = Material3Theme.colorScheme.surface,
                    contentColor = Material3Theme.colorScheme.onSurface
                ) {
                    Box(
                        modifier = Modifier
                            .safeDrawingPadding()
                            .safeGesturesPadding()
                    ) {
                        val appState = rememberAppState(snackBarHostState)

                        appState.navController.addOnDestinationChangedListener(navHostListener)

                        Material3Scaffold(
                            snackbarHost = {
                                Material3SnackbarHost(
                                    hostState = snackBarHostState,
                                    snackbar = { snackBarData ->
                                        KotoxSnackbar(
                                            snackBarData
                                        )
                                    }
                                )
                            },
                            bottomBar = {
                                AuthBottomNavigation(
                                    navController = appState.navController,
                                    firebaseUser = state,
                                    onLogout = { viewModel.onLogout() },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("bottom_navigation")
                                )
                            }
                        ) { innerPaddingModifier ->
                            AuthAppNavHost(
                                navController = appState.navController,
                                modifier = Modifier.padding(innerPaddingModifier),
                                closeAuthAndPopUp = { popup -> appState.navigateAndPopUp(ProfileRoute, popup) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@Composable
fun rememberAppState(
    snackbarHostState: Material3SnackbarHostState,
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarMessageHandler = SnackbarMessageHandler,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(snackbarHostState, navController, snackbarManager, resources, coroutineScope) {
        AuthAppState(snackbarHostState, navController, snackbarManager, resources, coroutineScope)
    }
