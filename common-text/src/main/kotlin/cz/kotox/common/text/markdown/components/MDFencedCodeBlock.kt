package com.aisense.otter.ui.markdown.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.kotox.common.text.markdown.MarkdownTheme
import org.commonmark.node.Document
import org.commonmark.node.FencedCodeBlock

@Composable
internal fun MDFencedCodeBlock(
    fencedCodeBlock: FencedCodeBlock,
    markdownTheme: MarkdownTheme,
    modifier: Modifier = Modifier
) {
    val padding = if (fencedCodeBlock.parent is Document) 8.dp else 0.dp
    Box(modifier = modifier.padding(start = 8.dp, bottom = padding)) {
        Text(
            text = fencedCodeBlock.literal,
            style = markdownTheme.fencedCodeBlockTextStyle,
            modifier = modifier
        )
    }
}