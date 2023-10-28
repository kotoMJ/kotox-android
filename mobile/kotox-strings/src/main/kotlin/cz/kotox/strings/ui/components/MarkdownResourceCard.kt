package cz.kotox.strings.ui.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import cz.kotox.android.strings.R
import cz.kotox.common.text.markdown.MDDocument
import cz.kotox.common.text.theme.KotoxMarkdownTheme
import cz.kotox.common.android.navigation.navigateToWeb

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
fun MarkdownResourceCard() {
    ResourceCard(titleRes = R.string.main_screen_card_markdown) {
        MarkdownResourceTextWithDefaultTheme()
    }
}

@Composable
private fun MarkdownResourceTextWithDefaultTheme() {
    val context = LocalContext.current
    MDDocument(
        stringResId = R.string.sample_text_markdown,
    )
    { link ->
        context.navigateToWeb(link)
    }
}

@Composable
private fun MarkdownResourceTextWithCustomTheme() {
    val context = LocalContext.current
    MDDocument(
        stringResId = R.string.sample_text_markdown,
        markdownTheme = KotoxMarkdownTheme,
    )
    { link ->
        context.navigateToWeb(link)
    }
}


@Composable
private fun MarkdownResourceTextWithCustomThemeExtra() {
    val context = LocalContext.current
    MDDocument(
        stringResId = R.string.sample_text_markdown,
        markdownTheme = KotoxMarkdownTheme.copy(
            linkTextStyle = KotoxMarkdownTheme.linkTextStyle.copy(
                color = Color.Red
            )
        ),
    )
    { link ->
        context.navigateToWeb(link)
    }
}