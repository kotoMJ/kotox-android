package cz.kotox.common.designsystem.theme.hornet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cz.kotox.common.designsystem.extension.conditional
import cz.kotox.common.designsystem.theme.shiraz.LocalColors

@Composable
fun HornetThemeWidgetPreview(
    fillMaxWidth: Boolean = false,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable () -> Unit
) {
    HornetAppTheme {
        Surface(
            modifier = Modifier
                .conditional(fillMaxWidth) {
                    fillMaxWidth()
                },
        ) {
            content()
        }
    }
}

@Composable
fun HornetThemeFullSizePreview(content: @Composable () -> Unit) {
    HornetAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}