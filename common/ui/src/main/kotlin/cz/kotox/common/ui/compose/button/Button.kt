package cz.kotox.common.ui.compose.button

import androidx.annotation.StringRes
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import cz.kotox.common.designsystem.preview.PreviewMobileLarge
import cz.kotox.common.designsystem.theme.orange.KotoxOrangeThemeWidgetPreview
import cz.kotox.common.designsystem.theme.shiraz.KotoxBasicThemeWidgetPreview
import cz.kotox.common.ui.R
import cz.kotox.common.ui.compose.extension.basicButton

@Composable
fun BasicButton(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    action: () -> Unit
) {
    Button(
        onClick = action,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        )
    ) {
        Text(text = stringResource(textRes), fontSize = 16.sp)
    }
}

@PreviewMobileLarge
@Composable
private fun BasicButtonShirazPreview() {
    KotoxBasicThemeWidgetPreview(fillMaxWidth = true) {
        BasicButton(
            textRes = R.string.general_button_ok,
            modifier = Modifier.basicButton()
        ) { /*Do nothing in preview */ }
    }
}

@PreviewMobileLarge
@Composable
private fun BasicButtonOrangePreview() {
    KotoxOrangeThemeWidgetPreview(fillMaxWidth = true) {
        BasicButton(
            textRes = R.string.general_button_ok,
            modifier = Modifier.basicButton()
        ) { /*Do nothing in preview */ }
    }
}
