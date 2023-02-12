package cz.kotox.core.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

//val Typography = Typography(
//    body1 = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    )
//)

@Immutable
data class KotoxBasicTypography(
    val headline: TextStyle,
    val body1Medium: TextStyle,
    val body1Regular: TextStyle,
    val body2Medium: TextStyle,
    val body2Regular: TextStyle
)

/**
 * letterSpacing converted with https://www.w3schools.com/cssref/css_pxtoemconversion.php
 */
val Typography = KotoxBasicTypography(
    headline = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = (0.0075).em,
    ),
    body1Medium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = (0.03125).em,
    ),
    body1Regular = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = (0.03125).em,
    ),
    body2Medium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = (0.0178).em,
    ),
    body2Regular = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = (0.0178).em,
    )
)

internal fun KotoxBasicTypography.toMaterialTypography(): Typography = Typography(
    defaultFontFamily = FontFamily.Default,
    h1 = headline,
    body1 = body1Regular,
    body2 = body2Regular
)