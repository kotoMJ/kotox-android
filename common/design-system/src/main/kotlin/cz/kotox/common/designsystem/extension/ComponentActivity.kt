package cz.kotox.common.designsystem.extension

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import cz.kotox.common.designsystem.theme.hornet.HornetNonComposeColors

fun ComponentActivity.enableEdgeToEdge(darkTheme: Boolean) {
    when {
        darkTheme ->
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.dark(
                    HornetNonComposeColors.surface.toArgb(),
                ),
                navigationBarStyle = SystemBarStyle.light(
                    HornetNonComposeColors.surface.toArgb(),
                    android.graphics.Color.TRANSPARENT
                )
            )

        else -> {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(
                    HornetNonComposeColors.surface.toArgb(),
                    android.graphics.Color.TRANSPARENT
                ),
                navigationBarStyle = SystemBarStyle.light(
                    HornetNonComposeColors.surface.toArgb(),
                    android.graphics.Color.TRANSPARENT
                )
            )
        }
    }
}