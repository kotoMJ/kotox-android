package cz.kotox.auth.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import cz.kotox.common.designsystem.component.navigation.KotoxNavigationBar
import cz.kotox.common.designsystem.component.navigation.KotoxNavigationBarItem

@Composable
internal fun AuthBottomNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val bottomNavVisible by remember(navController) {
        derivedStateOf {
            val currentDestination = navBackStackEntry?.destination
            when (currentDestination?.route) {
                LoginRoute -> true
                ProfileRoute -> true
                SettingRoute -> true
                else -> false
            }
        }
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = bottomNavVisible,
    ) {
        KotoxNavigationBar(
            modifier = Modifier.navigationBarsPadding(),
        ) {
            BottomNavScreen.values.forEach { screen ->
                val isSelected = remember(screen) {
                    derivedStateOf {
                        navBackStackEntry?.destination?.hierarchy
                            ?.any { it.route == screen.route } ?: false
                    }
                }

                KotoxNavigationBarItem(
                    modifier = Modifier.testTag(screen.testTag),
                    iconPainter = rememberVectorPainter(image = screen.iconImageVecot),
                    title = stringResource(id = screen.titleId),
                    selected = { isSelected.value },
                    onClick = {
                        navController.navigate(screen.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // re-selecting the same item
                            launchSingleTop = true
                            // Restore state when re-selecting a previously selected item
                            restoreState = true
                        }
                    },
                )
            }
        }
    }
}
