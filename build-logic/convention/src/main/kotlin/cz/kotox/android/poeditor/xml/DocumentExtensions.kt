package cz.kotox.android.poeditor.xml

import org.w3c.dom.Document
import org.w3c.dom.bootstrap.DOMImplementationRegistry
import org.w3c.dom.ls.DOMImplementationLS
import java.io.StringWriter
import java.lang.Boolean
import javax.xml.parsers.DocumentBuilderFactory

private val DEFAULT_ENCODING = Charsets.UTF_8

/**
 * Converts an XML string to a proper [Document].
 * If the string is an empty string, it generates a basic <resources> XML.
 */
fun String.toStringsXmlDocument(): Document {
    val xmlString = this.ifBlank {
        "<resources></resources>"
    }

    return DocumentBuilderFactory.newInstance()
        .newDocumentBuilder()
        .parse(xmlString.byteInputStream(DEFAULT_ENCODING))
}

/**
 * Converts a [Document] into a formatted [String].
 */
fun Document.toAndroidXmlString(): String {
    val registry = DOMImplementationRegistry.newInstance()
    val impl = registry.getDOMImplementation("LS") as DOMImplementationLS
    val output = impl.createLSOutput().apply { encoding = "utf-8" }
    val serializer = impl.createLSSerializer()

    val writer = StringWriter()
    output.characterStream = writer

    serializer.domConfig.setParameter(
        "format-pretty-print",
        Boolean.TRUE
    )
    serializer.domConfig.setParameter("xml-declaration", true)

    serializer.write(this, output)

    return writer.toString().unescapeHtmlTags()
}
