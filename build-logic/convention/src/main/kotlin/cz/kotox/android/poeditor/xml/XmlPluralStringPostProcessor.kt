package cz.kotox.android.poeditor.xml

import cz.kotox.android.poeditor.getModuleName
import org.w3c.dom.Element

private const val ROOT_ATTR_NAME = "name"

internal fun sanitizePluralRootElementAndClassifyTargetModule(
    nodeElement: Element,
): Pair<String, Element> {

    val copiedNodeElement: Element
    var moduleName = "plu"

    val (cDataNode, cDataPosition) = getCDataChildForNode(nodeElement)

    if (cDataNode == null) {
        val content = nodeElement.textContent
        val originalNameAttibute = nodeElement.getAttribute(ROOT_ATTR_NAME)
        val fixedNameAttribute =
            originalNameAttibute.replace(
                ATTR_NAME_IOS_DELIMITER,
                ATTR_NAME_ANDROID_DELIMITER
            )


        copiedNodeElement = (nodeElement.cloneNode(true) as Element).apply {
            setAttribute(ROOT_ATTR_NAME, fixedNameAttribute)
        }
        moduleName = getModuleName(originalNameAttibute)

        return Pair(moduleName, copiedNodeElement)
    } else {
        logger.warn("CDATA processing in string resources is not supported by this processor yet!")
        //Check https://github.com/hyperdevs-team/poeditor-android-gradle-plugin how to process CDATA
        return Pair(moduleName, nodeElement)
    }
}

