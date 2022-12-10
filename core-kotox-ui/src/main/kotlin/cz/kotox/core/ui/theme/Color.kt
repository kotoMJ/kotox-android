package cz.kotox.core.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

//https://chir.ag/projects/name-that-color

val Shiraz = Color(0xFFB10C43)
val Cinder = Color(0xFF0C0C14)
val StormGray = Color(0xFF727680)
val DoveGray = Color(0xFF706D6B)
val Concreate = Color(0xFFF3F3F3)
val White = Color(0xFFFFFFFF)

@Immutable
data class KotoxBasicColors(
    val primary: Color,
    val onPrimary: Color,
    val surface: Color,
    val background: Color,
    val error: Color,
    val onError: Color,
    val onControlsPrimary: Color,
    val onControlsSecondary: Color,
    val textNorm: Color,
    val textWeak: Color,
    val divider: Color,
    val isLight: Boolean,
)

internal fun KotoxBasicColors.toMaterialColors(): Colors = Colors(
    primary = primary,
    primaryVariant = primary,
    secondary = Color.Yellow,//FIXME MJ
    secondaryVariant = Color.Yellow,//FIXME MJ
    background = background,
    onBackground = textNorm,
    surface = surface,
    onSurface = textNorm,
    error = error,
    onError = onError,
    onPrimary = onPrimary,
    onSecondary = textNorm,
    isLight = isLight,
)