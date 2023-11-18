package cz.kotox.strings.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import cz.kotox.strings.R
import cz.kotox.common.core.android.navigation.navigateToWeb

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
fun SplitResourceCard() {
    ResourceCard(titleRes = R.string.main_screen_card_split) {
        SplitResourceText()
    }
}

private const val WEB_LINK_TAG = "web-link"
private const val WEB_LINK = "https://jenicek.dev"

@Composable
private fun SplitResourceText() {
    val context = LocalContext.current

    val richText = buildAnnotatedString {

        append(stringResource(id = R.string.sample_text_plain_1))

        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(stringResource(id = R.string.sample_text_plain_2))
        }

        withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
            append(stringResource(id = R.string.sample_text_plain_3))
        }

        append(stringResource(id = R.string.sample_text_plain_4))

        pushStringAnnotation(WEB_LINK_TAG, WEB_LINK)
        withStyle(
            style = SpanStyle(
                color = Color.Red,
                textDecoration = TextDecoration.Underline,
            )
        ) {
            append(stringResource(id = R.string.sample_text_plain_5))
        }
        pop()

        append(stringResource(id = R.string.sample_text_plain_6))

    }

    ClickableText(text = richText) { offset ->
        richText.getStringAnnotations(
            WEB_LINK_TAG,
            offset,
            offset
        ).firstOrNull()?.let {
            context.navigateToWeb(it.item)
        }
    }
}