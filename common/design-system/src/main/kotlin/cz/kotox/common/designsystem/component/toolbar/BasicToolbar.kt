package cz.kotox.common.designsystem.component.toolbar

import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@Composable
fun BasicToolbar(@StringRes title: Int) {
  TopAppBar(title = { Text(stringResource(title)) }, backgroundColor = toolbarColor(), contentColor = MaterialTheme.colorScheme.onSurface)
}

@Composable
fun BasicToolbar(title: String) {
  TopAppBar(title = { Text(title) }, backgroundColor = toolbarColor(), contentColor = MaterialTheme.colorScheme.onSurface)
}

@Composable
private fun toolbarColor(darkTheme: Boolean = isSystemInDarkTheme()): Color {
  return if (darkTheme) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surface
}