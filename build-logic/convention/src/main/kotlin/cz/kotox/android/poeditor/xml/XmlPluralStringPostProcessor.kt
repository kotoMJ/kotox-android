package cz.kotox.android.poeditor.xml

import cz.kotox.android.poeditor.TargetResource
import cz.kotox.android.poeditor.defaultTargetResource
import cz.kotox.android.poeditor.getTargetResource
import org.w3c.dom.Element

private const val ROOT_ATTR_NAME = "name"

internal fun sanitizePluralRootElementAndClassifyTargetModule(
    nodeElement: Element,
): Pair<TargetResource, Element> {

    val copiedNodeElement: Element
    var targetResrouce = defaultTargetResource

    val (cDataNode, cDataPosition) = getCDataChildForNode(nodeElement)

    if (cDataNode == null) {
        val content = nodeElement.textContent
        val originalNameAttibute = nodeElement.getAttribute(ROOT_ATTR_NAME)
        val fixedNameAttribute =
            originalNameAttibute.replace(
                ATTR_NAME_IOS_DELIMITER,
                ATTR_NAME_ANDROID_DELIMITER
            )


        /**
         * We need to sanitize child items via sanitizePluralChildItemElement() so DO NOT USE deep copy
         * as it would copy also non-sanitized children.
         */
        val deepCopy = false
        copiedNodeElement = (nodeElement.cloneNode(deepCopy) as Element).apply {
            setAttribute(ROOT_ATTR_NAME, fixedNameAttribute)
        }
        targetResrouce = getTargetResource(originalNameAttibute)

        return Pair(targetResrouce, copiedNodeElement)
    } else {
        logger.warn("CDATA processing in string resources is not supported by this processor yet!")
        //Check https://github.com/hyperdevs-team/poeditor-android-gradle-plugin how to process CDATA
        return Pair(targetResrouce, nodeElement)
    }
}

/**
 * Transform
 * DEV NOTE: transformation is simplified and thus does not supports CDATA processing!!!
 */
internal fun sanitizePluralChildItemElement(
    nodeElement: Element,
): Pair<TargetResource, Element> {

    val copiedNodeElement: Element
    val moduleName = defaultTargetResource

    val (cDataNode, cDataPosition) = getCDataChildForNode(nodeElement)

    if (cDataNode == null) {
        val content = nodeElement.textContent


        val processedContent = formatTranslationIOsParameterString(content)

        copiedNodeElement = (nodeElement.cloneNode(true) as Element).apply {
            textContent = processedContent
        }
        return Pair(moduleName, copiedNodeElement)
    } else {
        logger.warn("CDATA processing in string resources is not supported by this processor yet!")
        //Check https://github.com/hyperdevs-team/poeditor-android-gradle-plugin how to process CDATA
        return Pair(moduleName, nodeElement)
    }
}


