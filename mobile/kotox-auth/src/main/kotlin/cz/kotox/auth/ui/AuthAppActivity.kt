package cz.kotox.auth.ui

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import cz.kotox.auth.navigation.AuthAppState
import cz.kotox.auth.navigation.DASHBOARD_SCREEN
import cz.kotox.auth.navigation.authAppGraph
import cz.kotox.auth.ui.snackbar.SnackbarManager
import cz.kotox.common.designsystem.extension.enableEdgeToEdge
import cz.kotox.common.designsystem.extension.isDarkMode
import cz.kotox.common.designsystem.theme.hornet.HornetAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
class AuthAppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(isDarkMode())
        WindowCompat.setDecorFitsSystemWindows(window, true)
        super.onCreate(savedInstanceState)

        setContent {
            val snackBarHostState = remember { SnackbarHostState() }

            HornetAppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ) {
                    Box(
                        modifier = Modifier
                            .safeDrawingPadding()
                            .safeGesturesPadding()
                    ) {
                        val appState = rememberAppState(snackBarHostState)

                        Scaffold(
                            snackbarHost = {
                                SnackbarHost(
                                    hostState = snackBarHostState,
                                    modifier = Modifier.padding(8.dp),
                                    snackbar = { snackbarData ->
                                        Snackbar(snackbarData, contentColor = MaterialTheme.colorScheme.onPrimary)
                                    }
                                )
                            }
                        ) { innerPaddingModifier ->
                            NavHost(
                                navController = appState.navController,
                                startDestination = DASHBOARD_SCREEN,
                                modifier = Modifier.padding(innerPaddingModifier)
                            ) {
                                authAppGraph(appState)
                            }
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
    snackbarHostState: SnackbarHostState,
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(snackbarHostState, navController, snackbarManager, resources, coroutineScope) {
        AuthAppState(snackbarHostState, navController, snackbarManager, resources, coroutineScope)
    }