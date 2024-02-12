package cz.kotox.common.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import cz.kotox.common.designsystem.R
import cz.kotox.common.designsystem.extension.basicButton
import cz.kotox.common.designsystem.preview.PreviewMobileLarge
import cz.kotox.common.designsystem.theme.hornet.HornetThemeWidgetPreview
import cz.kotox.common.designsystem.theme.orange.KotoxOrangeThemeWidgetPreview
import cz.kotox.common.designsystem.theme.shiraz.KotoxBasicThemeWidgetPreview

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
            //containerColor = MaterialTheme.colorScheme.onBackground,

        ),
    ) {
        Text(text = stringResource(textRes), fontSize = 16.sp)
    }
}

@PreviewMobileLarge
@Composable
private fun BasicButtonNaturePreview() {
    HornetThemeWidgetPreview(fillMaxWidth = true) {
        BasicButton(
            textRes = R.string.general_button_ok,
            modifier = Modifier.basicButton()
        ) { /*Do nothing in preview */ }
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
