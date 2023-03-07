package cz.kotox.android.poeditor.xml

import cz.kotox.android.poeditor.TargetResource
import cz.kotox.android.poeditor.defaultTargetResource
import cz.kotox.android.poeditor.getTargetResource
import org.w3c.dom.Element

private const val ATTR_NAME = "name"
internal const val ATTR_NAME_IOS_DELIMITER = "."
internal const val ATTR_NAME_ANDROID_DELIMITER = "_"

/**
 * Transform
 * DEV NOTE: transformation is simplified and thus does not supports CDATA processing!!!
 */
internal fun sanitizeSingleStringElementAndClassifyTargetModule(
    nodeElement: Element,
): Pair<TargetResource, Element> {

    val copiedNodeElement: Element
    var targetResource = defaultTargetResource

    val (cDataNode, cDataPosition) = getCDataChildForNode(nodeElement)

    if (cDataNode == null) {
        val content = nodeElement.textContent
        val originalNameAttibute = nodeElement.getAttribute(ATTR_NAME)
        val fixedNameAttribute =
            originalNameAttibute.replace(
                ATTR_NAME_IOS_DELIMITER,
                ATTR_NAME_ANDROID_DELIMITER
            )

        val processedContent = formatTranslationIOsParameterString(content)

        copiedNodeElement = (nodeElement.cloneNode(true) as Element).apply {
            textContent = processedContent
            setAttribute(ATTR_NAME, fixedNameAttribute)
        }
        targetResource = getTargetResource(originalNameAttibute)

        return Pair(targetResource, copiedNodeElement)
    } else {
        logger.warn("CDATA processing in string resources is not supported by this processor yet!")
        //Check https://github.com/hyperdevs-team/poeditor-android-gradle-plugin how to process CDATA
        return Pair(targetResource, nodeElement)
    }
}
