package cz.kotox.common.ui

import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatDelegate
import timber.log.Timber

object ThemeUtils {

    private var appResources: Resources? = null


    /**
     * This is just theming util without need test in isolation so we can initialize it out of Hilt.
     */
    fun initialize(appResources: Resources) {
        ThemeUtils.appResources = appResources
    }

    private fun isDarkMode(): Boolean {
        val currentAppResources = appResources
        return if (currentAppResources == null) {
            //Do not throw exception here to keep Scaffold screen preview working.
            Timber.e(
                IllegalArgumentException(
                    "appResources is null! Did you forget to call ThemeUtils.initialize in the App.onCreate?"
                )
            )
            false
        } else {
            val darkModeFlag =
                currentAppResources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)
            darkModeFlag == Configuration.UI_MODE_NIGHT_YES
        }
    }

    fun isDarkThemeIncludingAppLocalSettingsNonComposable(): Boolean =
        when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_NO -> false
            AppCompatDelegate.MODE_NIGHT_YES -> true
            else -> isDarkMode()
        }
}