package cz.kotox.common.designsystem.theme.shiraz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cz.kotox.common.designsystem.extension.conditional

@Composable
fun KotoxBasicThemeWidgetPreview(
    fillMaxWidth: Boolean = false,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable () -> Unit
) {
    KotoxBasicTheme {
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
fun KotoxBasicThemeFullSizePreview(content: @Composable () -> Unit) {
    KotoxBasicTheme {
        Box(
            modifier = Modifier
                .background(LocalColors.current.background)
                .fillMaxSize()
        ) {
            content()
        }
    }
}