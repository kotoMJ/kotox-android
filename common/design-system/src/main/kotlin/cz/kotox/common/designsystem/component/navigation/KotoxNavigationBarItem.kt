package cz.kotox.common.designsystem.component.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.NavigationBarItem as Material3NavigationBarItem

@Composable
fun RowScope.KotoxNavigationBarItem(
    iconPainter: Painter,
    title: String,
    selected: () -> Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Material3NavigationBarItem(
        modifier = modifier,
        selected = selected(),
        onClick = onClick,
        icon = {
            Icon(
                painter = iconPainter,
                contentDescription = title,
            )
        },
        label = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
            )
        },
//        colors = MaterialNavigationBarItemDefaults.colors(
//            selectedIconColor = AndroidTemplateTheme.colors.content,
//            selectedTextColor = AndroidTemplateTheme.colors.content,
//            indicatorColor = AndroidTemplateTheme.colors.backgroundPrimary,
//            unselectedIconColor = AndroidTemplateTheme.colors.content.copy(alpha = 0.5f),
//            unselectedTextColor = AndroidTemplateTheme.colors.content.copy(alpha = 0.5f),
//        ),
    )
}
