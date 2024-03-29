package cz.kotox.android.poeditor.xml

import cz.kotox.android.poeditor.TargetResource
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
        private const val TAG_RESOURCES = "resources"
        private const val TAG_STRING = "string"
        private const val TAG_PLURALS = "plurals"
        private const val TAG_ITEM = "item"

    }


    fun postProcessTranslationXml(translationFileXmlString: String): Map<TargetResource, Document> {
        // Parse line by line by traversing the original file using DOM
        val translationFileXmlDocument = translationFileXmlString.toStringsXmlDocument()

        val elementMap = processAndCategorizeXmlElements(
            translationFileXmlDocument,
            translationFileXmlDocument.childNodes
        )

        return elementMap.map { (key, value) ->

            val moduleResourcesDocument = "".toStringsXmlDocument()

            val disclaimerCommentStart = moduleResourcesDocument.createComment(
                RESOURCE_FILE_DISCLAIMER_COMMENT
            )
            moduleResourcesDocument.adoptNode(disclaimerCommentStart)
            moduleResourcesDocument.documentElement.appendChild(disclaimerCommentStart)

            value.forEach {
                // Transfer ownership of the new node into the destination document
                moduleResourcesDocument.adoptNode(it)
                // Make the new node an actual item in the target document
                moduleResourcesDocument.documentElement.appendChild(it)
            }

            val disclaimerCommentEnd = moduleResourcesDocument.createComment(
                RESOURCE_FILE_DISCLAIMER_COMMENT
            )
            moduleResourcesDocument.adoptNode(disclaimerCommentEnd)
            moduleResourcesDocument.documentElement.appendChild(disclaimerCommentEnd)

            key to moduleResourcesDocument
        }.toMap()

    }


    /**
     * Splits an Android XML file to multiple XML files depending on regex matching.
     */

    private fun processAndCategorizeXmlElements(
        document: Document,
        nodeList: NodeList,
    ): MutableMap<TargetResource, MutableList<Element>> {
        val elemntMap: MutableMap<TargetResource, MutableList<Element>> = mutableMapOf()
        for (i in 0 until nodeList.length) {
            if (nodeList.item(i).nodeType == Node.ELEMENT_NODE) {
                val nodeElement = nodeList.item(i) as Element
                when (nodeElement.tagName) {
                    TAG_RESOURCES -> {
                        // Main node, traverse its children
                        val innerMap = processAndCategorizeXmlElements(
                            document,
                            nodeElement.childNodes,
                        )

                        innerMap.forEach { (key, value) ->
                            if (!elemntMap.containsKey(key)) {
                                elemntMap[key] = mutableListOf<Element>()
                            }
                            elemntMap[key]?.addAll(value)
                        }
                    }
                    TAG_PLURALS -> {
                        val pluralsRootElement =
                            sanitizePluralRootElementAndClassifyTargetModule(nodeElement)

                        if (pluralsRootElement != null) {
                            // Plurals node, process its children
                            val pluralsChildItemsMap = processAndCategorizeXmlElements(
                                document,
                                nodeElement.childNodes,
                            )

                            pluralsChildItemsMap.values.forEach { oneChildItemMap ->
                                oneChildItemMap.forEach { childItemNode ->
                                    // Transfer ownership of the new node into the destination document
                                    pluralsRootElement.second.ownerDocument.adoptNode(childItemNode)
                                    // Make the new node an actual item in the target document
                                    pluralsRootElement.second.appendChild(childItemNode)
                                }
                            }

                            if (!elemntMap.containsKey(pluralsRootElement.first)) {
                                elemntMap[pluralsRootElement.first] = mutableListOf<Element>()
                            }
                            elemntMap[pluralsRootElement.first]?.add(pluralsRootElement.second)
                        }

                    }
                    TAG_STRING -> {
                        val stringElement =
                            sanitizeSingleStringElementAndClassifyTargetModule(nodeElement)

                        if (stringElement != null) {
                            if (!elemntMap.containsKey(stringElement.first)) {
                                elemntMap[stringElement.first] = mutableListOf<Element>()
                            }

                            elemntMap[stringElement.first]?.add(stringElement.second)
                        }
                    }
                    TAG_ITEM -> {

                        val element = sanitizePluralChildItemElement(nodeElement)
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

}
