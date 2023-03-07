package cz.kotox.android.poeditor.xml

import cz.kotox.android.poeditor.getModuleName
import org.w3c.dom.Element

private val VARIABLE_REGEX = Regex("""\{\d?\{(.*?)\}\}""")

private const val ATTR_NAME = "name"
internal const val ATTR_NAME_IOS_DELIMITER = "."
internal const val ATTR_NAME_ANDROID_DELIMITER = "_"

/**
 * Transform
 * DEV NOTE: transformation is simplified and thus does not supports CDATA processing!!!
 */
internal fun sanitizeSingleStringElementAndClassifyTargetModule(
    nodeElement: Element,
): Pair<String, Element> {

    val copiedNodeElement: Element
    var moduleName = ""

    val (cDataNode, cDataPosition) = getCDataChildForNode(nodeElement)

    if (cDataNode == null) {
        val content = nodeElement.textContent
        val originalNameAttibute = nodeElement.getAttribute(ATTR_NAME)
        val fixedNameAttribute =
            originalNameAttibute.replace(
                ATTR_NAME_IOS_DELIMITER,
                ATTR_NAME_ANDROID_DELIMITER
            )

        val processedContent = formatTranslationString(content)

        copiedNodeElement = (nodeElement.cloneNode(true) as Element).apply {
            textContent = processedContent
            setAttribute(ATTR_NAME, fixedNameAttribute)
        }
        moduleName = getModuleName(originalNameAttibute)

        return Pair(moduleName, copiedNodeElement)
    } else {
        logger.warn("CDATA processing in string resources is not supported by this processor yet!")
        //Check https://github.com/hyperdevs-team/poeditor-android-gradle-plugin how to process CDATA
        return Pair(moduleName, nodeElement)
    }
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