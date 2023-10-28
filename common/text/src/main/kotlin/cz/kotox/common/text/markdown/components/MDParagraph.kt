package cz.kotox.common.text.markdown.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cz.kotox.common.text.markdown.MarkdownTheme
import cz.kotox.common.text.markdown.appendMarkdownChildren
import org.commonmark.node.Document
import org.commonmark.node.Image
import org.commonmark.node.Paragraph

@Composable
internal fun MDParagraph(
    paragraph: Paragraph,
    markdownTheme: MarkdownTheme,
    onTextClick: (text: String) -> Unit,
    modifier: Modifier = Modifier,
    textAlign: TextAlign?
) {
    if (paragraph.firstChild is Image && paragraph.firstChild == paragraph.lastChild) {
        // Paragraph with single image
        MDImage(paragraph.firstChild as Image, modifier)
    } else {
        val padding = if (paragraph.parent is Document) 8.dp else 0.dp
        Box(modifier = modifier.padding(bottom = padding)) {
            val styledText = buildAnnotatedString {
                pushStyle(
                    markdownTheme.paragraphTextStyle.toSpanStyle()
                )
                appendMarkdownChildren(paragraph, markdownTheme)
                pop()
            }
            MarkdownText(
                text = styledText,
                style = markdownTheme.paragraphTextStyle,
                onTextClick = onTextClick,
                textAlign = textAlign
            )
        }
    }
}