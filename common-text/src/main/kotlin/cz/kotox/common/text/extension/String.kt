package cz.kotox.common.text.extension

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

fun String.bold() = buildAnnotatedString {
    pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
    append(this@bold)
    pop()
}

fun String.bold(text: String): AnnotatedString {
    val parts = this.split(text)
    return buildAnnotatedString {
        parts.forEachIndexed { index, part ->
            append(part)
            if (index != parts.lastIndex) {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(text)
                }
            }
        }
    }
}

fun String.withBoldParts(delimiter: String = "**"): AnnotatedString {
    val parts = this.split(delimiter)
    return buildAnnotatedString {
        parts.forEachIndexed { index, part ->
            if (index % 2 != 0) {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(part)
                }
            } else {
                append(part)
            }
        }
    }
}