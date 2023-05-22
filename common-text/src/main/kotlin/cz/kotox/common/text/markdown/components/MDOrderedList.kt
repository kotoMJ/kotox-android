package cz.kotox.common.text.markdown.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import cz.kotox.common.text.markdown.MarkdownTheme
import cz.kotox.common.text.markdown.appendMarkdownChildren
import cz.kotox.common.text.markdown.components.MDListItems
import cz.kotox.common.text.markdown.components.MarkdownText
import org.commonmark.node.OrderedList

@Composable
internal fun MDOrderedList(
    orderedList: OrderedList,
    markdownTheme: MarkdownTheme,
    onTextClick: (text: String) -> Unit,
    modifier: Modifier = Modifier,
    textAlign: TextAlign?
) {
    var number = orderedList.startNumber
    val delimiter = orderedList.delimiter
    MDListItems(
        listBlock = orderedList,
        markdownTheme = markdownTheme,
        modifier = modifier,
        onTextClick = onTextClick,
        textAlign = textAlign
    ) {
        val text = buildAnnotatedString {
            pushStyle(markdownTheme.orderedListTextStyle.toSpanStyle())
            append("${number++}$delimiter ")
            appendMarkdownChildren(it, markdownTheme)
            pop()
        }
        MarkdownText(
            text = text,
            style = markdownTheme.orderedListTextStyle,
            onTextClick = onTextClick,
            modifier = modifier,
            textAlign = textAlign
        )
    }
}