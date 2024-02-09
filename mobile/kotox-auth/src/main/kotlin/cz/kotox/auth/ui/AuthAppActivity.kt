package cz.kotox.auth.ui

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import cz.kotox.auth.navigation.AuthAppState
import cz.kotox.auth.navigation.DASHBOARD_SCREEN
import cz.kotox.auth.navigation.authAppGraph
import cz.kotox.auth.ui.snackbar.SnackbarManager
import cz.kotox.common.designsystem.theme.orange.KotoxOrangeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
class AuthAppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KotoxOrangeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val appState = rememberAppState()

                    Scaffold(
                        snackbarHost = {
                            SnackbarHost(
                                hostState = it,
                                modifier = Modifier.padding(8.dp),
                                snackbar = { snackbarData ->
                                    Snackbar(snackbarData, contentColor = MaterialTheme.colors.onPrimary)
                                }
                            )
                        },
                        scaffoldState = appState.scaffoldState
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

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(scaffoldState, navController, snackbarManager, resources, coroutineScope) {
        AuthAppState(scaffoldState, navController, snackbarManager, resources, coroutineScope)
    }
