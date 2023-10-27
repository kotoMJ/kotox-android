package cz.kotox.common.text.markdown.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import cz.kotox.common.text.markdown.MarkdownTheme
import cz.kotox.common.text.markdown.appendMarkdownChildren
import org.commonmark.node.BlockQuote

@Composable
internal fun MDBlockQuote(
    blockQuote: BlockQuote,
    markdownTheme: MarkdownTheme,
    modifier: Modifier = Modifier
) {
    val color = MaterialTheme.colors.onBackground
    Box(
        modifier = modifier
            .drawBehind {
                drawLine(
                    color = color,
                    strokeWidth = 2f,
                    start = Offset(12.dp.value, 0f),
                    end = Offset(12.dp.value, size.height)
                )
            }
            .padding(start = 16.dp, top = 4.dp, bottom = 4.dp)) {
        val text = buildAnnotatedString {
            pushStyle(
                markdownTheme.blockQuoteTextStyle.toSpanStyle()
                    .plus(SpanStyle(fontStyle = FontStyle.Italic))
            )
            appendMarkdownChildren(blockQuote, markdownTheme)
            pop()
        }
        Text(text, modifier)
    }
}