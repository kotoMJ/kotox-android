package cz.kotox.android.poeditor.xml

/**
 * Unescapes HTML tags from string.
 */
fun String.unescapeHtmlTags() = this.replace("&lt;", "<").replace("&gt;", ">")
