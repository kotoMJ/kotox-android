package cz.kotox.common.text.markdown

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

data class MarkdownTheme(
    val colors: Colors,
    val textStyle: TextStyle,
    val linkTextStyle: TextStyle,
    val codeTextStyle: TextStyle,
    val strongEmphasisTextStyle: TextStyle,
    val emphasisTextStyle: TextStyle,
    val fencedCodeBlockTextStyle: TextStyle,
    val orderedListTextStyle: TextStyle,
    val bulletListTextStyle: TextStyle,
    val paragraphTextStyle: TextStyle,
    val blockQuoteTextStyle: TextStyle,
)

val DefaultMarkdownTheme: MarkdownTheme
    @Composable
    get() = MarkdownTheme(
        colors = MaterialTheme.colors,
        textStyle = TextStyle(),
        linkTextStyle = TextStyle(
            color = MaterialTheme.colors.primary,
            textDecoration = TextDecoration.Underline
        ),
        codeTextStyle = TextStyle(
            fontFamily = FontFamily.Monospace
        ),
        strongEmphasisTextStyle = TextStyle(
            fontWeight = FontWeight.Bold
        ),
        emphasisTextStyle = TextStyle(
            fontStyle = FontStyle.Italic
        ),
        fencedCodeBlockTextStyle = TextStyle(fontFamily = FontFamily.Monospace),
        orderedListTextStyle = MaterialTheme.typography.body1,
        bulletListTextStyle = MaterialTheme.typography.body1,
        paragraphTextStyle = MaterialTheme.typography.body1,
        blockQuoteTextStyle = MaterialTheme.typography.body1
    )

