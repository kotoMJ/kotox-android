package cz.kotox.android.poeditor.xml


private val ANDROID_VARIABLE_REGEX = Regex("""\{\d?\{(.*?)\}\}""")

/**
 * Formats a given string to conform to Android strings.xml format.
 */
internal fun String.replaceUniversalParameterString(): String {
    // We need to check for variables to see if we have to escape percent symbols: if we find variables, we have
    // to escape them
    val containsVariables = this.contains(ANDROID_VARIABLE_REGEX)

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

    return this
        // Replace % with %% if variables are found
        .let { if (containsVariables) it.replace("%", "%%") else it }
        .unescapeHtmlTags()
        // Replace placeholders from {{variable}} to %1$s format.
        .replace(ANDROID_VARIABLE_REGEX, placeholderTransform)
}

private val IOS_VARIABLE_REGEX_STRING = Regex("""(%@)""")
private val IOS_VARIABLE_REGEX_LONG = Regex("""(%ld)""")
private val IOS_VARIABLE_REGEX_FLOAT = Regex("""(%f)""")
private val IOS_VARIABLE_REGEX_INT = Regex("""(%d)""")
private val IOS_VARIABLE_REGEX_INT2 = Regex("""(%i)""")

/**
 * Formats a given string to conform to Android strings.xml format.
 */
internal fun String.replaceIOsParameterString(): String =
    this
        .unescapeHtmlTags()
        .replace(IOS_VARIABLE_REGEX_INT, "%d")
        .replace(IOS_VARIABLE_REGEX_INT2, "%d")
        .replace(IOS_VARIABLE_REGEX_LONG, "%d")
        .replace(IOS_VARIABLE_REGEX_FLOAT, "%f")
        .replace(IOS_VARIABLE_REGEX_STRING, "%s")
