package cz.kotox.common.designsystem.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cz.kotox.common.designsystem.theme.KotoxBasicTheme
import cz.kotox.common.designsystem.theme.LocalColors

@Composable
fun KotoxBasicThemeWidgetPreview(content: @Composable () -> Unit) {
    KotoxBasicTheme {
        Box(modifier = Modifier.background(LocalColors.current.background)) {
            content()
        }
    }
}

@Composable
fun KotoxBasicThemeFullSizePreview(content: @Composable () -> Unit) {
    KotoxBasicTheme {
        Box(
            modifier = Modifier
                .background(LocalColors.current.background).fillMaxSize()
        ) {
            content()
        }
    }
}