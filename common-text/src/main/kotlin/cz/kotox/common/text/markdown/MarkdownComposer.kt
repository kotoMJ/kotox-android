package cz.kotox.common.text.markdown

import androidx.annotation.StringRes
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import com.aisense.otter.ui.markdown.components.MDBlockQuote
import com.aisense.otter.ui.markdown.components.MDBulletList
import com.aisense.otter.ui.markdown.components.MDFencedCodeBlock
import com.aisense.otter.ui.markdown.components.MDHeading
import com.aisense.otter.ui.markdown.components.MDImage
import com.aisense.otter.ui.markdown.components.MDIndentedCodeBlock
import com.aisense.otter.ui.markdown.components.MDOrderedList
import com.aisense.otter.ui.markdown.components.MDParagraph
import com.aisense.otter.ui.markdown.components.MDThematicBreak
import org.commonmark.node.BlockQuote
import org.commonmark.node.BulletList
import org.commonmark.node.Code
import org.commonmark.node.Document
import org.commonmark.node.Emphasis
import org.commonmark.node.FencedCodeBlock
import org.commonmark.node.HardLineBreak
import org.commonmark.node.Heading
import org.commonmark.node.Image
import org.commonmark.node.IndentedCodeBlock
import org.commonmark.node.Link
import org.commonmark.node.Node
import org.commonmark.node.OrderedList
import org.commonmark.node.Paragraph
import org.commonmark.node.StrongEmphasis
import org.commonmark.node.Text
import org.commonmark.node.ThematicBreak
import org.commonmark.parser.Parser
import timber.log.Timber

/**
 *
 * https://commonmark.org/
 * https://spec.commonmark.org/
 *
 *
 * The following is an example of how to use this:
 * ```
 * val parser = Parser.builder().build()
 * val root = parser.parse(MIXED_MD) as Document
 * val markdownComposer = MarkdownComposer()
 *
 * MarkdownComposerTheme {
 *    MDDocument(root)
 * }
 * ```
 */

//Common mark does not support empty lines in paragraphs, but this is the workaround.
const val COMMONMARK_EMPTY_LINE = """```    ```"""

internal const val TAG_URL = "url"
internal const val TAG_IMAGE_URL = "imageUrl"

@Composable
fun MDDocument(
    text: String,
    markdownTheme: MarkdownTheme = DefaultMarkdownTheme,
    textAlign: TextAlign? = null,
    onTextClick: (text: String) -> Unit = {}
) {
    val parser = Parser.builder().build()
    val document = parser.parse(text) as Document
    MDBlockChildren(parent = document, markdownTheme = markdownTheme, onTextClick = onTextClick, textAlign = textAlign)
}

@Composable
fun MDDocument(
    @StringRes stringResId: Int,
    markdownTheme: MarkdownTheme = DefaultMarkdownTheme,
    textAlign: TextAlign? = null,
    onTextClick: (text: String) -> Unit = {}
) {
    val parser = Parser.builder().build()
    val document = parser.parse(stringResource(id = stringResId)) as Document
    MDBlockChildren(parent = document, markdownTheme = markdownTheme, onTextClick = onTextClick, textAlign = textAlign)
}

@Composable
fun MDDocument(
    document: Document,
    markdownTheme: MarkdownTheme = DefaultMarkdownTheme,
    textAlign: TextAlign? = null,
    onTextClick: (text: String) -> Unit = {}
) {
    MDBlockChildren(parent = document, markdownTheme = markdownTheme, onTextClick = onTextClick, textAlign = textAlign)
}

@Composable
internal fun MDBlockChildren(
    parent: Node,
    markdownTheme: MarkdownTheme,
    textAlign: TextAlign?,
    onTextClick: (text: String) -> Unit
) {
    var child = parent.firstChild
    while (child != null) {
        Timber.d(">>>_ NODE: $child")
        when (child) {
            is BlockQuote -> MDBlockQuote(blockQuote = child, markdownTheme = markdownTheme)
            is ThematicBreak -> MDThematicBreak(child)
            is Heading -> MDHeading(heading = child, onTextClick = onTextClick, markdownTheme = markdownTheme, textAlign = textAlign)
            is Paragraph -> MDParagraph(paragraph = child, markdownTheme = markdownTheme, onTextClick = onTextClick, textAlign = textAlign)
            is FencedCodeBlock -> MDFencedCodeBlock(fencedCodeBlock = child, markdownTheme = markdownTheme)
            is IndentedCodeBlock -> MDIndentedCodeBlock(child)
            is Image -> MDImage(child)
            is BulletList -> MDBulletList(bulletList = child, markdownTheme = markdownTheme, onTextClick = onTextClick, textAlign = textAlign)
            is OrderedList -> MDOrderedList(orderedList = child, markdownTheme = markdownTheme, onTextClick = onTextClick, textAlign = textAlign)
        }
        child = child.next
    }
}

internal fun AnnotatedString.Builder.appendMarkdownChildren(
    parent: Node,
    markdownTheme: MarkdownTheme
) {
    var child = parent.firstChild
    while (child != null) {
        when (child) {
            is Paragraph -> appendMarkdownChildren(child, markdownTheme)
            is Text -> {
                pushStyle(markdownTheme.textStyle.toSpanStyle())
                append(child.literal)
                pop()
            }

            is Image -> appendInlineContent(TAG_IMAGE_URL, child.destination)
            is Emphasis -> {
                pushStyle(markdownTheme.emphasisTextStyle.toSpanStyle())
                appendMarkdownChildren(child, markdownTheme)
                pop()
            }

            is StrongEmphasis -> {
                pushStyle(markdownTheme.strongEmphasisTextStyle.toSpanStyle())
                appendMarkdownChildren(child, markdownTheme)
                pop()
            }

            is Code -> {
                pushStyle(markdownTheme.codeTextStyle.toSpanStyle())
                append(child.literal)
                pop()
            }

            is HardLineBreak -> {
                append("\n")
            }

            is Link -> {
                pushStyle(markdownTheme.linkTextStyle.toSpanStyle())
                pushStringAnnotation(TAG_URL, child.destination)
                appendMarkdownChildren(child, markdownTheme)
                pop()
                pop()
            }
        }
        child = child.next
    }
}