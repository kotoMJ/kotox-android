package cz.kotox.android.poeditor.xml

import org.w3c.dom.CDATASection
import org.w3c.dom.Element
import org.w3c.dom.Node

/**
 * @return Pair<Node?, cDataPosition>
 */
internal fun getCDataChildForNode(nodeElement: Element): Pair<Node?, Int> {
    val childrenList = nodeElement.childNodes
    for (i in 0..childrenList.length) {
        val childNode = childrenList.item(i)
        if (childNode is CDATASection) {
            return Pair(childNode, i)
        }
    }
    return Pair(null, -1)
}