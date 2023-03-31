package cz.kotox.android.poeditor.xml

import cz.kotox.android.poeditor.TargetResource
import org.w3c.dom.Document
import java.io.File

internal const val RESOURCES_PATH = "/src/main/res"
internal const val RESOURCES_FOLDER = "values"
private const val RESOURCES_FILE_NAME_START = "strings_"
private const val RESOURCES_FILE_NAME_END = "_shared"
private const val RESOURCES_FILE_SUFFIX = "xml"

/**
 * Class that converts XML data into Android XML files.
 */
class AndroidXmlWriter {

    /**
     * Saves a given map of XML files related to a language that the project contains to the
     * project's strings folder.
     */
    fun saveXml(
        projectDirPath: String,
        postProcessedXmlDocumentMap: Map<TargetResource, Document>,
    ) {
        val resDirPath = "${projectDirPath}$RESOURCES_PATH"
        val baseValuesDir = File(File(resDirPath), RESOURCES_FOLDER)

        postProcessedXmlDocumentMap.forEach { (targetResrouce, document) ->

            if (projectDirPath.endsWith(targetResrouce.moduleName)) {
                val resFileName =
                    "$RESOURCES_FILE_NAME_START${targetResrouce.resourceName.trim()}$RESOURCES_FILE_NAME_END"
                saveXmlToFolder(baseValuesDir, document, resFileName)
            }
        }
    }

    fun saveXmlToFolder(stringsFolderFile: File, document: Document, resFileName: String) {
        if (!stringsFolderFile.exists()) {
            logger.debug("Creating strings folder for new language")
            val folderCreated = stringsFolderFile.mkdirs()
            logger.debug("Folder created?: $folderCreated")
            check(folderCreated) { "Strings folder could not be created: ${stringsFolderFile.absolutePath}" }
        }

        //logger.lifecycle("Saving strings to ${stringsFolderFile.absolutePath}")
        File(stringsFolderFile, "$resFileName.$RESOURCES_FILE_SUFFIX").writeText(document.toAndroidXmlString())
    }
}
