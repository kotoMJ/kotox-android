package com.aisense.otter.ui.markdown.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cz.kotox.common.text.markdown.MarkdownTheme
import org.commonmark.node.BulletList
import org.commonmark.node.Document
import org.commonmark.node.ListBlock
import org.commonmark.node.Node
import org.commonmark.node.OrderedList

@Composable
internal fun MDListItems(
    listBlock: ListBlock,
    markdownTheme: MarkdownTheme,
    modifier: Modifier = Modifier,
    textAlign: TextAlign?,
    onTextClick: (text: String) -> Unit,
    item: @Composable (node: Node) -> Unit
) {
    val bottom = if (listBlock.parent is Document) 8.dp else 0.dp
    val start = if (listBlock.parent is Document) 0.dp else 8.dp
    Column(modifier = modifier.padding(start = start, bottom = bottom)) {
        var listItem = listBlock.firstChild
        while (listItem != null) {
            var child = listItem.firstChild
            while (child != null) {
                when (child) {
                    is BulletList -> MDBulletList(
                        bulletList = child,
                        markdownTheme = markdownTheme,
                        onTextClick = onTextClick,
                        modifier = modifier,
                        textAlign = textAlign
                    )

                    is OrderedList -> MDOrderedList(
                        orderedList = child,
                        markdownTheme = markdownTheme,
                        onTextClick = onTextClick,
                        modifier = modifier,
                        textAlign = textAlign
                    )

                    else -> item(child)
                }
                child = child.next
            }
            listItem = listItem.next
        }
    }
}