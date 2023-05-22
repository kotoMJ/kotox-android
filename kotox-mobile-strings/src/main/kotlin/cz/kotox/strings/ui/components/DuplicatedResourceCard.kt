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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import cz.kotox.android.strings.R
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
fun DuplicatedResourceCard() {
    ResourceCard(titleRes = R.string.main_screen_card_duplicated) {
        DuplicatedResourceText()
    }
}

private const val WEB_LINK_TAG = "web-link"
private const val WEB_LINK = "https://jenicek.dev"

@Composable
private fun DuplicatedResourceText() {
    val context = LocalContext.current

    val plainText = stringResource(id = R.string.sample_text_plain)
    val linkPart = stringResource(id = R.string.sample_text_plain_link)
    val richText = buildAnnotatedString {
        append(plainText)

        val boldPart = stringResource(id = R.string.sample_text_plain_bold)
        addStyle(
            SpanStyle(fontWeight = FontWeight.Bold),
            plainText.indexOf(boldPart),
            plainText.indexOf(boldPart) + boldPart.length
        )

        val italicPart = stringResource(id = R.string.sample_text_plain_italic)
        addStyle(
            SpanStyle(fontStyle = FontStyle.Italic),
            plainText.indexOf(italicPart),
            plainText.indexOf(italicPart) + italicPart.length
        )

        addStringAnnotation(
            WEB_LINK_TAG, WEB_LINK, plainText.indexOf(linkPart),
            plainText.indexOf(linkPart) + linkPart.length
        )
        addStyle(
            SpanStyle(
                color = Color.Red,
                textDecoration = TextDecoration.Underline,
            ),
            plainText.indexOf(linkPart),
            plainText.indexOf(linkPart) + linkPart.length
        )
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