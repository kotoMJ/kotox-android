package cz.kotox.android.poeditor.xml

import cz.kotox.android.poeditor.createValuesModifierFromLangCode
import org.w3c.dom.Document
import java.io.File

/**
 * Class that converts XML data into Android XML files.
 */
class AndroidXmlWriter {

    /**
     * Saves a given map of XML files related to a language that the project contains to the
     * project's strings folder.
     */
    @Suppress("LongParameterList")
    fun saveXml(
        resDirPath: String,
        postProcessedXmlDocumentMap: Map<String, Document>,
        defaultLang: String,
        languageCode: String,
        languageValuesOverridePathMap: Map<String, String>?
    ) {
        // First check if we have passed a default "values" folder for the given language
        var baseValuesDir: File? =
            languageValuesOverridePathMap?.get(languageCode)?.let { File(it) }

        // If we haven't passed a default base values directory, compose the base values folder
        if (baseValuesDir == null) {
            var valuesFolderName = "values"

            val valuesModifier = createValuesModifierFromLangCode(languageCode)
            if (valuesModifier != defaultLang) valuesFolderName =
                "$valuesFolderName-$valuesModifier"

            baseValuesDir = File(File(resDirPath), valuesFolderName)
        }

        postProcessedXmlDocumentMap.forEach { (moduleName, document) ->
            logger.lifecycle(">>>_ moduleName: $moduleName")
            val resFileName =
                if (moduleName.trim().isEmpty()) "strings" else "strings_${moduleName}"
            saveXmlToFolder(baseValuesDir, document, resFileName)
        }
    }

    fun saveXmlToFolder(stringsFolderFile: File, document: Document, resFileName: String) {
        if (!stringsFolderFile.exists()) {
            logger.debug("Creating strings folder for new language")
            val folderCreated = stringsFolderFile.mkdirs()
            logger.debug("Folder created?: $folderCreated")
            check(folderCreated) { "Strings folder could not be created: ${stringsFolderFile.absolutePath}" }
        }

        logger.lifecycle("Saving strings to ${stringsFolderFile.absolutePath}")
        File(stringsFolderFile, "$resFileName.xml").writeText(document.toAndroidXmlString())
    }
}
