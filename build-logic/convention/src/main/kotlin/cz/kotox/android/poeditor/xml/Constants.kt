package cz.kotox.android.poeditor.xml

import org.gradle.api.logging.Logging

val logger = Logging.getLogger(org.gradle.api.logging.Logger::class.java)!!
internal const val ATTR_NAME_IOS_SUFFIX = "_ios"
internal const val ATTR_NAME_ANDROID_SUFFIX = "_android"
internal const val ATTR_NAME_IOS_DELIMITER = "."
internal const val ATTR_NAME_ANDROID_DELIMITER = "_"
internal const val RESOURCE_FILE_DISCLAIMER_COMMENT = " GENERATED RESOURCE FILE. DO NOT MODIFY! Check README-STRING-RESOURCES.md for more details "