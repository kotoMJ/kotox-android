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


    fun postProcessTranslationXml(
        translationFileXmlString: String
    ): Map<String, Document> =

        splitTranslationXml(
            formatTranslationXml(translationFileXmlString)
        )


    fun formatTranslationXml(translationFileXmlString: String): String {
        // Parse line by line by traversing the original file using DOM
        val translationFileXmlDocument = translationFileXmlString.toStringsXmlDocument()

        formatTranslationXmlDocument(
            translationFileXmlDocument,
            translationFileXmlDocument.childNodes
        )

        return translationFileXmlDocument.toAndroidXmlString()
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
    fun splitTranslationXml(translationXmlString: String): Map<String, Document> {
        val translationFileRecords = translationXmlString.toStringsXmlDocument()

//        val ret =  fileSplitRegexStringList
//            .map { regex ->
//                // Extract strings matched by patterns to a separate strings XML
//                regex to extractMatchingNodes(translationFileRecords.childNodes, regex)
//            }
//            .filter { (_, nodes) -> nodes.isNotEmpty() } // Only process suffixes with at least one coincidence
//            .toMap()
//            .mapValues { (regexString, nodes) ->
//                val regex = Regex(regexString)
//                val xmlString = "<resources></resources>"
//                val xmlRecords = xmlString.toStringsXmlDocument()
//                nodes.forEach { node ->
//                    node.parentNode.removeChild(node)
//                    val copiedNode = (node.cloneNode(true) as Element).apply {
//                        val name = getAttribute(ATTR_NAME)
//                        val nameWithoutRegex = regex.find(name)?.groups?.get(1)?.value ?: ""
//                        setAttribute(ATTR_NAME, nameWithoutRegex)
//                    }
//                    xmlRecords.adoptNode(copiedNode)
//                    xmlRecords.firstChild.appendChild(copiedNode)
//                }
//
//                // Remove empty nodes from resulting XMLs
//                sanitizeNodes(xmlRecords)
//                xmlRecords
//            }

        val ret = mapOf<String, Document>()
            .plus(ALL_REGEX_STRING to translationFileRecords)

        logger.lifecycle(">>>_ map: ${ret}")
        return ret
    }

    private fun formatTranslationXmlDocument(
        document: Document,
        nodeList: NodeList,
        rootNode: Node? = null
    ) {
        for (i in 0 until nodeList.length) {
            if (nodeList.item(i).nodeType == Node.ELEMENT_NODE) {
                val nodeElement = nodeList.item(i) as Element
                when (nodeElement.tagName) {
                    TAG_RESOURCES -> {
                        // Main node, traverse its children
                        formatTranslationXmlDocument(document, nodeElement.childNodes, nodeElement)
                    }
                    TAG_PLURALS -> {
                        // Plurals node, process its children
                        formatTranslationXmlDocument(document, nodeElement.childNodes, nodeElement)
                    }
                    TAG_STRING -> {
                        // String node, apply transformation to the content
                        processTextAndReplaceNodeContent(document, nodeElement, rootNode)
                    }
                    TAG_ITEM -> {
                        // Plurals item node, apply transformation to the content
                        processTextAndReplaceNodeContent(document, nodeElement, rootNode)
                    }
                }
            }
        }
    }

    private fun processTextAndReplaceNodeContent(
        document: Document,
        nodeElement: Element,
        rootNode: Node?
    ) {
        // First check if we have a CDATA node as the a child of the element. If we have it, we have to
        // preserve the CDATA node but process the text. Else, we handle the node as a usual text node
        val copiedNodeElement: Element
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
        }

        document.adoptNode(copiedNodeElement)
        rootNode?.replaceChild(copiedNodeElement, nodeElement)
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
