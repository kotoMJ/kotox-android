package cz.kotox.android.poeditor

import cz.kotox.android.poeditor.xml.logger


/**
 * Creates values file modifier taking into account specializations (i.e values-es-rMX for Mexican).
 * @param langCode
 * @return proper values file modifier (i.e. es-rMX)
 */
fun createValuesModifierFromLangCode(langCode: String): String {
    val langParts = langCode.split("-")
    val language = langParts[0]
    val region = langParts.getOrNull(1)?.toLowerCase()

    //DEV NOTE: simplified variant, does not cover chinese support (zh), and old locales (he, yi, id)
    return "$language${region?.let { "-r${it.toUpperCase()}" } ?: ""}"
}