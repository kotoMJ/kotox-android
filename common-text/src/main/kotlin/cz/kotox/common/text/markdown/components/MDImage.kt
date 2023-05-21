package com.aisense.otter.ui.markdown.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import org.commonmark.node.Image

@Composable
internal fun MDImage(image: Image, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Image(
            painter = rememberImagePainter(
                data = image.destination,
                builder = {
                    size(OriginalSize)
                },
            ),
            contentDescription = null,
        )
    }
}
