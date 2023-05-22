package cz.kotox.strings.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import cz.kotox.android.strings.R
import cz.kotox.common.text.markdown.MDDocument
import cz.kotox.common.text.theme.KotoxMarkdownTheme
import cz.kotox.core.android.navigation.navigateToWeb
import cz.kotox.core.ui.theme.KotoxBasicTheme
import cz.kotox.core.ui.theme.LocalColors
import cz.kotox.core.ui.theme.LocalTypography

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
        MarkdownResourceText()
    }
}

@Composable
private fun MarkdownResourceText() {
    val context = LocalContext.current
    MDDocument(
        stringResId = R.string.sample_text_markdown,
        markdownTheme = KotoxMarkdownTheme.copy(
            paragraphTextStyle = TextStyle(
                color = LocalColors.current.textNorm,
                fontFamily = FontFamily.Default,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            )
        )
    ) {
        context.navigateToWeb(it)
    }
}