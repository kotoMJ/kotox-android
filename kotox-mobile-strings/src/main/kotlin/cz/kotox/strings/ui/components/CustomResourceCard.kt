package cz.kotox.strings.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import cz.kotox.android.strings.R
import cz.kotox.common.text.extension.withBoldParts
import cz.kotox.core.android.navigation.navigateToWeb

@Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Light Mode"
)

@Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun CustomResourceCard() {
    ResourceCard(titleRes = R.string.main_screen_card_custom) {
        CustomResourceText()
    }
}


@Composable
private fun CustomResourceText() {

    val richText = stringResource(id = R.string.sample_text_custom).withBoldParts()

    Text(text = richText)
}