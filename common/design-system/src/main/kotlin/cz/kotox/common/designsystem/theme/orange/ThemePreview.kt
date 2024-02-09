package cz.kotox.common.designsystem.theme.orange

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cz.kotox.common.designsystem.extension.conditional
import cz.kotox.common.designsystem.theme.shiraz.LocalColors

@Composable
fun KotoxOrangeThemeWidgetPreview(
    fillMaxWidth: Boolean = false,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable () -> Unit
) {
    KotoxOrangeTheme {
        Box(
            modifier = Modifier
                .background(LocalColors.current.background)
                .conditional(fillMaxWidth) {
                    fillMaxWidth()
                },
            contentAlignment = contentAlignment
        ) {
            content()
        }
    }
}

@Composable
fun KotoxOrangeThemeFullSizePreview(content: @Composable () -> Unit) {
    KotoxOrangeTheme {
        Box(
            modifier = Modifier
                .background(LocalColors.current.background)
                .fillMaxSize()
        ) {
            content()
        }
    }
}