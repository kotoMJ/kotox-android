package cz.kotox.auth.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import cz.kotox.auth.R
import cz.kotox.feature.firebase.auth.model.FirebaseUser

sealed interface BottomNavScreen {
    val route: String

    val iconImageVecot: ImageVector

    @get:StringRes
    val titleId: Int

    val testTag: String

    data object Login : BottomNavScreen {
        override val route: String = AuthRoute
        override val iconImageVecot = Icons.AutoMirrored.Filled.Login
        override val titleId = R.string.bottom_screen_title_auth
        override val testTag = "bottom_nav_item_login"
    }

    data object Logout : BottomNavScreen {
        override val route: String = "tbd."
        override val iconImageVecot = Icons.AutoMirrored.Filled.Logout
        override val titleId = R.string.bottom_screen_title_logout
        override val testTag = ""
    }

    data object Profile : BottomNavScreen {
        override val route: String = ProfileRoute
        override val iconImageVecot = Icons.Filled.Person
        override val titleId = R.string.bottom_screen_title_profile
        override val testTag = "bottom_nav_item_profile"
    }

    data object Settings : BottomNavScreen {
        override val route: String = SettingRoute
        override val iconImageVecot = Icons.Filled.Settings
        override val titleId = R.string.bottom_screen_title_settings
        override val testTag = "bottom_nav_item_settings"
    }

    companion object {
        val values = listOf(
            Login,
            Logout,
            Profile,
            Settings
        )

        fun valuesBasedOnCurrentUser(user: FirebaseUser) =
            values.filterNot {
                it in if (user == FirebaseUser.None) {
                    listOf(Logout, Profile)
                } else {
                    listOf(Login)
                }
            }
    }
}
