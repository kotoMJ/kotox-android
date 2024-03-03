package cz.kotox.common.designsystem.component.button

import androidx.annotation.StringRes
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import cz.kotox.common.designsystem.R
import cz.kotox.common.designsystem.extension.basicButton
import cz.kotox.common.designsystem.preview.PreviewTheme
import cz.kotox.common.designsystem.theme.hornet.HornetThemeWidgetPreview

@Composable
fun FilledTonalButton(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    action: () -> Unit
) {
    FilledTonalButton(
        onClick = action,
        enabled = enabled,
        modifier = modifier,
    ) {
        Text(text = stringResource(textRes), fontSize = 16.sp)
    }
}

@PreviewTheme
@Composable
private fun BasicButtonHornetPreview() {
    HornetThemeWidgetPreview(fillMaxWidth = true) {
        FilledTonalButton(
            textRes = R.string.general_button_ok,
            modifier = Modifier.basicButton()
        ) { /*Do nothing in preview */ }
    }
}
