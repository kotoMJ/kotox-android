package cz.kotox.common.designsystem.component.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.KeyOff
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.Dp
import cz.kotox.common.designsystem.preview.PreviewTheme
import cz.kotox.common.designsystem.theme.hornet.HornetThemeWidgetPreview
import androidx.compose.material3.NavigationBar as Material3NavigationBar

@Composable
fun KotoxNavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.background),
    elevation: Dp = NavigationBarDefaults.Elevation,
    content: @Composable RowScope.() -> Unit,
) {
    Material3NavigationBar(
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        tonalElevation = elevation,
        content = content,
    )
}

@PreviewTheme
@Composable
internal fun AndroidTemplateNavigationBarPreview() {
    HornetThemeWidgetPreview {
        KotoxNavigationBar {

            KotoxNavigationBarItem(
                iconPainter = rememberVectorPainter(image = Icons.Filled.Person),
                title = "Profile",
                selected = { false },
                onClick = {},
            )

            KotoxNavigationBarItem(
                iconPainter = rememberVectorPainter(image = Icons.Filled.Key),
                title = "Sign In",
                selected = { true },
                onClick = {},
            )

            KotoxNavigationBarItem(
                iconPainter = rememberVectorPainter(image = Icons.Filled.KeyOff),
                title = "Sign Out",
                selected = { true },
                onClick = {},
            )

            KotoxNavigationBarItem(
                iconPainter = rememberVectorPainter(image = Icons.Filled.Settings),
                title = "Settings",
                selected = { false },
                onClick = {},
            )

        }
    }
}
