package cz.kotox.android.poeditor.xml

import org.w3c.dom.CDATASection
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList


/**
 * Class that handles XML transformation.
 */
@Suppress("StringLiteralDuplication")
class XmlPostProcessor {
    companion object {
        private val DEFAULT_ENCODING = Charsets.UTF_8
        private val VARIABLE_REGEX = Regex("""\{\d?\{(.*?)\}\}""")

        private const val TAG_RESOURCES = "resources"
        private const val TAG_STRING = "string"
        private const val TAG_PLURALS = "plurals"
        private const val TAG_ITEM = "item"

        private const val ATTR_NAME = "name"
    }


    fun postProcessTranslationXml(translationFileXmlString: String): Map<String, Document> {
        // Parse line by line by traversing the original file using DOM
        val translationFileXmlDocument = translationFileXmlString.toStringsXmlDocument()

        val elementMap = formatTranslationXmlDocument(
            translationFileXmlDocument,
            translationFileXmlDocument.childNodes
        )


        return elementMap.map { (key, value) ->

            val moduleResourcesDocument = "".toStringsXmlDocument()
            value.forEach {
                logger.lifecycle(">>>_ node: ${it.textContent}")
                // Transfer ownership of the new node into the destination document
                moduleResourcesDocument.adoptNode(it)
                // Make the new node an actual item in the target document
                moduleResourcesDocument.documentElement.appendChild(it)
            }

            key to moduleResourcesDocument
        }.toMap()

    }

    /**
     * Formats a given string to conform to Android strings.xml format.
     */
    fun formatTranslationString(translationString: String): String {
        // We need to check for variables to see if we have to escape percent symbols: if we find variables, we have
        // to escape them
        val containsVariables = translationString.contains(VARIABLE_REGEX)

        val placeholderTransform: (MatchResult) -> CharSequence = { matchResult ->
            // Pending: if the string has multiple variables but any of them has no order number,
            //  throw an exception

            // If the placeholder contains an ordinal, use it: {2{pages_count}} -> %2$s
            val match = matchResult.groupValues[0]
            if (Character.isDigit(match[1])) {
                "%${match[1]}\$s"
            } else { // If not, use "1" as the ordinal: {{pages_count}} -> %1$s
                "%1\$s"
            }
        }

        return translationString
            // Replace % with %% if variables are found
            .let { if (containsVariables) it.replace("%", "%%") else it }
            .unescapeHtmlTags()
            // Replace placeholders from {{variable}} to %1$s format.
            .replace(VARIABLE_REGEX, placeholderTransform)
    }

    /**
     * Splits an Android XML file to multiple XML files depending on regex matching.
     */

    private fun formatTranslationXmlDocument(
        document: Document,
        nodeList: NodeList,
    ): MutableMap<String, MutableList<Element>> {
        val elemntMap: MutableMap<String, MutableList<Element>> = mutableMapOf()
        for (i in 0 until nodeList.length) {
            if (nodeList.item(i).nodeType == Node.ELEMENT_NODE) {
                val nodeElement = nodeList.item(i) as Element
                when (nodeElement.tagName) {
                    TAG_RESOURCES -> {
                        // Main node, traverse its children
                        val innerMap = formatTranslationXmlDocument(
                            document,
                            nodeElement.childNodes,
                        )

                        innerMap.forEach { key, value ->
                            if (!elemntMap.containsKey(key)) {
                                elemntMap[key] = mutableListOf<Element>()
                            }
                            elemntMap[key]?.addAll(value)
                        }
                    }
                    TAG_PLURALS -> {
                        // Plurals node, process its children
                        val innerMap = formatTranslationXmlDocument(
                            document,
                            nodeElement.childNodes,
                        )

                        innerMap.forEach { key, value ->
                            if (!elemntMap.containsKey(key)) {
                                elemntMap[key] = mutableListOf<Element>()
                            }
                            elemntMap[key]?.addAll(value)
                        }
                    }
                    TAG_STRING -> {
                        // String node, apply transformation to the content
                        val element = transformElement(nodeElement)
                        if (!elemntMap.containsKey(element.first)) {
                            elemntMap[element.first] = mutableListOf<Element>()
                        }

                        elemntMap[element.first]?.add(element.second)
                    }
                    TAG_ITEM -> {
                        // Plurals item node, apply transformation to the content
                        val element = transformElement(nodeElement)
                        if (!elemntMap.containsKey(element.first)) {
                            elemntMap[element.first] = mutableListOf<Element>()
                        }
                        elemntMap[element.first]?.add(element.second)
                    }
                }
            }
        }

        return elemntMap
    }

    private fun transformElement(
        //document: Document,
        nodeElement: Element,
        //rootNode: Node?
    ): Pair<String, Element> {
        // First check if we have a CDATA node as the a child of the element. If we have it, we have to
        // preserve the CDATA node but process the text. Else, we handle the node as a usual text node
        val copiedNodeElement: Element
        var moduleName = ""
        val (cDataNode, cDataPosition) = getCDataChildForNode(nodeElement)
        if (cDataNode != null) {
            val cDataContent = cDataNode.textContent
            val processedCDataContent = formatTranslationString(cDataContent)
            val copiedCDataNode = (cDataNode.cloneNode(true) as CDATASection).apply {
                this.data = processedCDataContent
            }
            copiedNodeElement = (nodeElement.cloneNode(true) as Element).apply {
                replaceChild(copiedCDataNode, this.childNodes.item(cDataPosition))
            }
        } else {
            val content = nodeElement.textContent
            val originalNameAttibute = nodeElement.getAttribute("name")
            val fixedNameAttribute = originalNameAttibute.replace(".", "_")
            val processedContent = formatTranslationString(content)

            copiedNodeElement = (nodeElement.cloneNode(true) as Element).apply {
                textContent = processedContent
                setAttribute("name", fixedNameAttribute)
            }
            moduleName = if (fixedNameAttribute.startsWith("addWallet")) "wallet" else ""
        }

        return Pair(moduleName, copiedNodeElement)
    }

    private fun getCDataChildForNode(nodeElement: Element): Pair<Node?, Int> {
        val childrenList = nodeElement.childNodes
        for (i in 0..childrenList.length) {
            val childNode = childrenList.item(i)
            if (childNode is CDATASection) {
                return Pair(childNode, i)
            }
        }
        return Pair(null, -1)
    }

    @Suppress("NestedBlockDepth")
    private fun extractMatchingNodes(nodeList: NodeList, regexString: String): List<Node> {
        val matchedNodes = mutableListOf<Node>()
        val regex = Regex(regexString)

        for (i in 0 until nodeList.length) {
            if (nodeList.item(i).nodeType == Node.ELEMENT_NODE) {
                val nodeElement = nodeList.item(i) as Element
                when (nodeElement.tagName) {
                    TAG_RESOURCES -> {
                        // Main XML node, process children
                        matchedNodes.addAll(
                            extractMatchingNodes(
                                nodeElement.childNodes,
                                regexString
                            )
                        )
                    }
                    TAG_STRING -> {
                        // String node, add node if name matches regex
                        if (nodeElement.getAttribute(ATTR_NAME).matches(regex)) {
                            matchedNodes.add(nodeElement)
                        }
                    }
                    TAG_PLURALS -> {
                        // Plurals node, add node and children if name matches regex
                        if (nodeElement.getAttribute(ATTR_NAME).matches(regex)) {
                            matchedNodes.add(nodeElement)
                        }
                    }
                }
            }
        }
        return matchedNodes
    }

    /**
     * Removes empty nodes for better serialization
     *
     * Extracted from https://stackoverflow.com/a/31421664/9288365
     */
    private fun sanitizeNodes(node: Node) {
        var child: Node? = node.firstChild
        while (child != null) {
            val sibling = child.nextSibling
            if (child.nodeType == Node.TEXT_NODE) {
                if (child.textContent.trim { it <= ' ' }.isEmpty()) {
                    node.removeChild(child)
                }
            } else {
                sanitizeNodes(child)
            }
            child = sibling
        }
    }
}
