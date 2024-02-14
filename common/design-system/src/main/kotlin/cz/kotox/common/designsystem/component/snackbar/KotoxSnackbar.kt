package cz.kotox.common.designsystem.component.snackbar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme as Material3Theme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun KotoxSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    actionOnNewLine: Boolean = false,
    shape: Shape = RoundedCornerShape(8.dp),
    backgroundColor: Color = Material3Theme.colorScheme.inverseSurface,
    contentColor: Color = Material3Theme.colorScheme.inverseOnSurface,
    actionColor: Color = Material3Theme.colorScheme.inversePrimary,
) = Snackbar(
    snackbarData = snackbarData,
    modifier = modifier.shadow(elevation = 0.dp),
    actionOnNewLine = actionOnNewLine,
    shape = shape,
    containerColor = backgroundColor,
    contentColor = contentColor,
    actionColor = actionColor,
)