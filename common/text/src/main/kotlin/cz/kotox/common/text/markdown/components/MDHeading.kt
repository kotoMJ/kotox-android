package cz.kotox.common.text.markdown.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cz.kotox.common.text.markdown.MDBlockChildren
import cz.kotox.common.text.markdown.MarkdownTheme
import cz.kotox.common.text.markdown.appendMarkdownChildren
import org.commonmark.node.Document
import org.commonmark.node.Heading

@Composable
internal fun MDHeading(
    heading: Heading,
    markdownTheme: MarkdownTheme,
    onTextClick: (text: String) -> Unit,
    modifier: Modifier = Modifier,
    textAlign: TextAlign?
) {
    val style = when (heading.level) {
        1 -> MaterialTheme.typography.h1
        2 -> MaterialTheme.typography.h2
        3 -> MaterialTheme.typography.h3
        4 -> MaterialTheme.typography.h4
        5 -> MaterialTheme.typography.h5
        6 -> MaterialTheme.typography.h6
        else -> {
            // Invalid header...
            MDBlockChildren(
                parent = heading,
                markdownTheme = markdownTheme,
                onTextClick = onTextClick,
                textAlign = textAlign
            )
            return
        }
    }

    val padding = if (heading.parent is Document) 8.dp else 0.dp
    Box(modifier = modifier.padding(bottom = padding)) {
        val text = buildAnnotatedString {
            appendMarkdownChildren(heading, markdownTheme)
        }
        MarkdownText(text = text, style = style, onTextClick = onTextClick, textAlign = textAlign)
    }
}