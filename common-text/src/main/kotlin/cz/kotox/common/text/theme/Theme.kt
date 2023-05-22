package cz.kotox.common.text.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import cz.kotox.common.text.markdown.DefaultMarkdownTheme
import cz.kotox.common.text.markdown.MarkdownTheme

val androidx.compose.material.Colors.linkTextColor: Color
    get() = if (isLight) Color(0xFF126FD6) else Color(0xFFB3D4FF)

val KotoxMarkdownTheme: MarkdownTheme
    @Composable
    get() = DefaultMarkdownTheme.copy(
        textStyle = TextStyle(
            fontFamily = FontFamily.Default
        ),
        linkTextStyle = TextStyle(
            color = MaterialTheme.colors.linkTextColor,
            textDecoration = TextDecoration.Underline,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = FontFamily.Default
        ),
        codeTextStyle = TextStyle(
            fontFamily = FontFamily.Monospace,
        ),
        paragraphTextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Default,
            fontSize = 14.sp,
            lineHeight = 20.sp,
        ),
        strongEmphasisTextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default
        ),
        emphasisTextStyle = TextStyle(
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Default
        ),
    )