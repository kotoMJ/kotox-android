package cz.kotox.common.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import cz.kotox.common.designsystem.ThemeUtils

val LightColorPalette = KotoxBasicColors(
    primary = Shiraz,
    onPrimary = White,
    surface = White,
    background = White,
    error = Color.Red,//FIXME MJ
    onError = White,
    onControlsPrimary = Cinder,
    onControlsSecondary = StormGray,
    textNorm = Cinder,
    textWeak = DoveGray,
    divider = Concreate,
    isLight = true
)

//TODO this is just hacky dark color solution without designs
val DarkColorPalette = KotoxBasicColors(
    primary = Shiraz,
    onPrimary = White,
    surface = Cinder,
    background = Cinder,
    error = Color.Red,//FIXME MJ
    onError = White,
    onControlsPrimary = White,
    onControlsSecondary = StormGray,
    textNorm = White,
    textWeak = DoveGray,
    divider = Concreate,
    isLight = false
)


val LocalColors =
    compositionLocalOf {
        if (ThemeUtils.isDarkThemeIncludingAppLocalSettingsNonComposable()) {
            DarkColorPalette
        } else {
            LightColorPalette
        }
    }


val LocalTypography = compositionLocalOf { Typography }

@Composable
fun KotoxBasicTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides Typography,
    ) {
        MaterialTheme(
            colors = colors.toMaterialColors(),
            typography = Typography.toMaterialTypography(),
            shapes = Shapes,
            content = content
        )
    }

    /**
     * Adjust system bar colors.
     */
    val systemUiController = rememberSystemUiController()
    if (darkTheme) {
        systemUiController.setSystemBarsColor(
            color = MaterialTheme.colors.background,
            darkIcons = false
        )
    } else {
        systemUiController.setSystemBarsColor(
            color = MaterialTheme.colors.background,
            darkIcons = true
        )
    }
}



