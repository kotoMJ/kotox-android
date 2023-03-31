package cz.kotox.android.poeditor.xml

import cz.kotox.android.poeditor.TargetResource
import cz.kotox.android.poeditor.defaultTargetResource
import cz.kotox.android.poeditor.getTargetResource
import org.w3c.dom.Element

private const val ATTR_NAME = "name"

/**
 * Transform
 * DEV NOTE: transformation is simplified and thus does not supports CDATA processing!!!
 */
internal fun sanitizeSingleStringElementAndClassifyTargetModule(
    nodeElement: Element,
): Pair<TargetResource, Element>? {

    val copiedNodeElement: Element
    var targetResource = defaultTargetResource

    val (cDataNode, _) = getCDataChildForNode(nodeElement)

    if (cDataNode == null) {
        val content = nodeElement.textContent
        val originalNameAttibute = nodeElement.getAttribute(ATTR_NAME)

        if (originalNameAttibute.endsWith(ATTR_NAME_IOS_SUFFIX)) {
            return null
        } else {
            val fixedNameAttribute = originalNameAttibute.removeSuffix(ATTR_NAME_ANDROID_SUFFIX).replace(
                ATTR_NAME_IOS_DELIMITER,
                ATTR_NAME_ANDROID_DELIMITER
            )

            copiedNodeElement = (nodeElement.cloneNode(true) as Element).apply {
                textContent = content.replaceIOsParameterString()
                setAttribute(ATTR_NAME, fixedNameAttribute)
            }
            targetResource = getTargetResource(originalNameAttibute)

            return Pair(targetResource, copiedNodeElement)
        }
    } else {
        logger.warn("CDATA processing in string resources is not supported by this processor yet!")
        //Check https://github.com/hyperdevs-team/poeditor-android-gradle-plugin how to process CDATA
        return Pair(targetResource, nodeElement)
    }
}
