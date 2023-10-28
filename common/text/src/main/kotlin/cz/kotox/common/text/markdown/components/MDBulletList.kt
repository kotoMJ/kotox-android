package cz.kotox.common.text.markdown.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import cz.kotox.common.text.markdown.MarkdownTheme
import cz.kotox.common.text.markdown.appendMarkdownChildren
import org.commonmark.node.BulletList

@Composable
internal fun MDBulletList(
    bulletList: BulletList,
    markdownTheme: MarkdownTheme,
    onTextClick: (text: String) -> Unit,
    modifier: Modifier = Modifier,
    textAlign: TextAlign?
) {
    val marker = bulletList.bulletMarker
    MDListItems(
        listBlock = bulletList,
        markdownTheme = markdownTheme,
        modifier = modifier,
        onTextClick = onTextClick,
        textAlign = textAlign
    ) {
        val text = buildAnnotatedString {
            pushStyle(markdownTheme.bulletListTextStyle.toSpanStyle())
            append("$marker ")
            appendMarkdownChildren(it, markdownTheme)
            pop()
        }
        MarkdownText(
            text = text,
            markdownTheme.bulletListTextStyle,
            onTextClick = onTextClick,
            modifier = modifier,
            textAlign = textAlign
        )
    }
}